package ru.itmo.tripService.controller;

import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import ru.itmo.tripService.client.CarFeignClient;
import ru.itmo.tripService.client.UserFeignClient;
import ru.itmo.tripService.kafka.model.CarControlMessage;
import ru.itmo.tripService.model.Trip;
import ru.itmo.tripService.model.TripStatus;
import ru.itmo.tripService.service.TripService;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/trip")
@AllArgsConstructor
public class RootController {

    private final TripService service;

    private final CarFeignClient carClient;
    private final UserFeignClient userClient;

    private final KafkaTemplate<String, CarControlMessage> kafkaTemplate;

    @GetMapping("/pickup")
    public Trip pickUp(@RequestParam Double start_lat,
                       @RequestParam Double start_long,
                       @RequestParam Double finish_lat,
                       @RequestParam Double finish_long,
                       @RequestHeader("Authorization") String token) {

        Long userId = userClient.approveToken(token);
        Long carId = carClient.findNearestCar(start_lat, start_long);
        Trip trip = new Trip(null, userId, carId, TripStatus.WAITING, null, null,
                start_lat, start_long, finish_lat, finish_long);

        trip = service.save(trip);

        kafkaTemplate.send("carControl",
                new CarControlMessage(trip.getId(), carId, start_lat, start_long));

        return trip;
    }

    @PostMapping("/interrupt")
    public void interrupt(@RequestParam Long id,
                          @RequestHeader("Authorization") String token) {
        Trip trip = service.getById(id);
        Long userId = userClient.approveToken(token);

        if (!trip.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Unauthorized");
        }

        trip.setStatus(TripStatus.FINISHED);
        trip.setFinishTime(LocalDateTime.now());
        carClient.finish(trip.getCarId());
        service.save(trip);
    }
}
