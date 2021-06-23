package ru.itmo.tripService.controller;

import org.springframework.web.bind.annotation.*;
import ru.itmo.tripService.model.Trip;

@RestController
@RequestMapping("/trip")
public class RootController {

    @GetMapping("/pickup")
    public Trip pickUp(@RequestParam Double latitude,
                       @RequestParam Double longitude) {
        return new Trip(latitude, longitude);
    }

    @PostMapping("/interrupt")
    public Integer interrupt(@RequestParam Integer id) {
        return id;
    }
}
