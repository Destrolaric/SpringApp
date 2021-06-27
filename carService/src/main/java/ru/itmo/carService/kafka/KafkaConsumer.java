package ru.itmo.carService.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.itmo.carService.kafka.model.CarControlMessage;
import ru.itmo.carService.kafka.model.TripControlMessage;
import ru.itmo.carService.model.Car;
import ru.itmo.carService.model.CarStatus;
import ru.itmo.carService.service.CarService;

@Component
public class KafkaConsumer {

    @Value("${travelling.steps}")
    private Integer tripSteps;

    @Value("${travelling.interval}")
    private Integer tripInterval;

    @Autowired
    private CarService service;

    @Autowired
    private KafkaTemplate<String, TripControlMessage> kafkaTemplate;

    @KafkaListener(topics = "carControl", groupId = "cars",
            containerFactory = "carsKafkaListenerContainerFactory")
    public void receiveCarControl(CarControlMessage message) {
        System.out.println(message);

        Car car = service.getById(message.getCarId());
        car.setStatus(CarStatus.BUSY);
        service.save(car);
        new Thread(() -> goTo(message, car.getLatitude(), car.getLongitude())).start();
    }

    private void goTo(CarControlMessage message,
                      Double start_lat, Double start_long) {
        Double lat_step = (message.getLatitude() - start_lat) / tripSteps;
        Double long_step = (message.getLongitude() - start_long) / tripSteps;
        for (int i = 0; i < tripSteps; ++i) {
            Car car = service.getById(message.getCarId());

            if (car.getStatus() == CarStatus.BUSY) {
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
