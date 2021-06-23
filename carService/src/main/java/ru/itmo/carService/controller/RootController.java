package ru.itmo.carService.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.carService.model.Car;

@RestController
@RequestMapping("/car")
public class RootController {

    @GetMapping("/findNearest")
    public Car findNearest(@RequestParam Double latitude,
                           @RequestParam Double longitude) {
        return new Car(1, null, null, latitude, longitude);
    }
}
