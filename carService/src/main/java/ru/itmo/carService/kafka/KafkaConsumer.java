package ru.itmo.carService.kafka;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.itmo.carService.model.Car;
import ru.itmo.carService.repository.CrudCarRepository;
import ru.itmo.carService.service.CarService;

@Component
@AllArgsConstructor
public class KafkaConsumer {

    final private int tripIters = 5;
    final private int tripInterval = 2; // in seconds

    private final CarService service;

    @KafkaListener(topics = "carControl", groupId = "cars",
            containerFactory = "carsKafkaListenerContainerFactory")
    public void receiveCarControl(CarControlMessage message) {
        System.out.println(message);

        int carId = message.getCarId();
        Car car = service.getById(carId);
        if (car.getStatus().equals("VACANT")) {
            car.setStatus("BUSY");
            service.save(car);
            new Thread(() -> goTo(carId, car.getLatitude(), car.getLongitude(),
                    message.getLatitude(), message.getLatitude())).start();
        } else {
            throw new RuntimeException("car with id:" + carId + " is not vacant");
        }
    }

    private void goTo(int carId,
                      Double start_lat, Double start_long,
                      Double finish_lat, Double finish_long) {
        Double lat_step = (finish_lat - start_lat) / tripIters;
        Double long_step = (finish_long - start_long) / tripIters;
        for (int i = 0; i < tripIters; ++i) {
            Car car = service.getById(carId);

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
    }
}
