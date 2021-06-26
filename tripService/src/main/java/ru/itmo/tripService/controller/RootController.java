package ru.itmo.tripService.controller;

import lombok.AllArgsConstructor;
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
import ru.itmo.tripService.service.TripService;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/trip")
@AllArgsConstructor
public class RootController {

    @Autowired
    private final TripService service;

    @Autowired
    private final CarFeignClient carClient;

    @Autowired
    private final KafkaTemplate<String, CarControlMessage> kafkaTemplate;

    @GetMapping("/pickup")
    public Trip pickUp(@RequestParam Double start_lat,
                       @RequestParam Double start_long,
                       @RequestParam Double finish_lat,
                       @RequestParam Double finish_long) {

        User user = new User(1,"123", "123", "123", "123");
        Car car = carClient.findNearestCar(start_lat, start_long);
        Trip trip = new Trip(null, user, car, TripStatus.WAITING, null, null,
                start_lat, start_long, finish_lat, finish_long);

        trip = service.save(trip);

        kafkaTemplate.send("carControl",
                new CarControlMessage(trip.getId(), car.getId(), start_lat, start_long));

        return trip;
    }

    @PostMapping("/interrupt")
    public void interrupt(@RequestParam Integer id) {
        Trip trip = service.getByIdEager(id);
        trip.setStatus(TripStatus.FINISHED);
        trip.setFinishTime(LocalDateTime.now());
        carClient.finish(trip.getCar().getId());
        service.save(trip);
    }
}
