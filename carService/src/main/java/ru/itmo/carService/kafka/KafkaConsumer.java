package ru.itmo.carService.kafka;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.itmo.carService.model.Car;
import ru.itmo.carService.repository.CrudCarRepository;
import ru.itmo.carService.service.CarService;

@Component
@AllArgsConstructor
public class KafkaConsumer {

    //TODO: replace it to config
    final private int tripIters = 5;
    final private int tripInterval = 2; // in seconds

    private final CarService service;

    @Autowired
    private KafkaTemplate<String, TripControlMessage> kafkaTemplate;

    @KafkaListener(topics = "carControl", groupId = "cars",
            containerFactory = "carsKafkaListenerContainerFactory")
    public void receiveCarControl(CarControlMessage message) {
        System.out.println(message);

        Car car = service.getById(message.getCarId());
        car.setStatus("BUSY");
        service.save(car);
        new Thread(() -> goTo(message, car.getLatitude(), car.getLongitude())).start();
    }

    private void goTo(CarControlMessage message,
                      Double start_lat, Double start_long) {
        Double lat_step = (message.getLatitude() - start_lat) / tripIters;
        Double long_step = (message.getLongitude() - start_long) / tripIters;
        for (int i = 0; i < tripIters; ++i) {
            Car car = service.getById(message.getCarId());

            if (car.getStatus().equals("BUSY")) {
                car.setLatitude(car.getLatitude() + lat_step);
                car.setLongitude(car.getLongitude() + long_step);
                service.save(car);
                System.out.println(car);
            } else {
                return;
            }

            try {
                Thread.sleep(tripInterval * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        kafkaTemplate.send("tripControl",
                new TripControlMessage(message.getTripId(), message.getCarId()));
    }
}
