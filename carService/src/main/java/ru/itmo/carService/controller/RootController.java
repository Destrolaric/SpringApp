package ru.itmo.carService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.carService.model.Car;
import ru.itmo.carService.service.CarService;

@RestController
@RequestMapping("/car")
public class RootController {

    @Autowired
    CarService service;

    @GetMapping("/findNearest")
    public Car findNearest(@RequestParam Double latitude,
                           @RequestParam Double longitude) {
        return service.findNearestCar(latitude, longitude);
    }
}
