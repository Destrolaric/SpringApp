package ru.itmo.tripService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.itmo.carService.model.Car;
import ru.itmo.tripService.model.Trip;
import ru.itmo.tripService.model.TripStatus;
import ru.itmo.tripService.model.User;
import ru.itmo.tripService.repository.CrudTripRepository;

@RestController
@RequestMapping("/trip")
public class RootController {

    @Autowired
    CrudTripRepository repository;

    @GetMapping("/pickup")
    public Trip pickUp(@RequestParam Double start_lat,
                       @RequestParam Double start_long,
                       @RequestParam Double finish_lat,
                       @RequestParam Double finish_long) {

        User user = new User(1, "123", "123", "123");
        Car car = new Car(1, "123", "123", 100.1, 300.2);
        Trip trip = new Trip(null, user, car, TripStatus.WAITING,
                start_lat, start_long, finish_lat, finish_long);

        repository.save(trip);
        return trip;
    }

    @PostMapping("/interrupt")
    public Integer interrupt(@RequestParam Integer id) {
        return id;
    }
}
