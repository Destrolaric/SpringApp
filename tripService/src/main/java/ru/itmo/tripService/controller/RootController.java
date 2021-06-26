package ru.itmo.tripService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.*;
import ru.itmo.tripService.client.CarFeignClient;
import ru.itmo.tripService.kafka.CarControlMessage;
import ru.itmo.tripService.model.Car;
import ru.itmo.tripService.model.Trip;
import ru.itmo.tripService.model.TripStatus;
import ru.itmo.tripService.model.User;
import ru.itmo.tripService.repository.CrudTripRepository;

@RestController
@RequestMapping("/trip")
public class RootController {

    @Autowired
    private CrudTripRepository repository;

    @Autowired
    private CarFeignClient carClient;

    @Autowired
    private KafkaTemplate<String, CarControlMessage> kafkaTemplate;

    @GetMapping("/pickup")
    public Trip pickUp(@RequestParam Double start_lat,
                       @RequestParam Double start_long,
                       @RequestParam Double finish_lat,
                       @RequestParam Double finish_long) {

        User user = new User(1,"123", "123", "123", "123");
        Car car = carClient.findNearestCar(start_lat, start_long);
        Trip trip = new Trip(null, user, car, TripStatus.WAITING, null, null,
                start_lat, start_long, finish_lat, finish_long);

        kafkaTemplate.send("carControl",
                new CarControlMessage(car.getId(), start_lat, start_long));

        repository.save(trip);
        return trip;
    }

    @PostMapping("/interrupt")
    public void interrupt(@RequestParam Integer id) {
        Trip trip = repository.getById(id);
        trip.setStatus(TripStatus.FINISHED);
        //TODO: request to carService to change car status
        repository.save(trip);
    }
}
