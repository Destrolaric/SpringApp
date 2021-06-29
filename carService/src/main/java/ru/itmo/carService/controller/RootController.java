package ru.itmo.carService.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.itmo.carService.model.Car;
import ru.itmo.carService.model.CarStatus;
import ru.itmo.carService.service.CarService;

@RestController
@RequestMapping("/car")
@AllArgsConstructor
public class RootController {

    private final CarService service;

    @GetMapping("/findNearest")
    public Long findNearest(@RequestParam Double latitude,
                            @RequestParam Double longitude) {
        return service.findNearestCar(latitude, longitude).getId();
    }

    @PostMapping("/finishTrip")
    public void finish(@RequestParam Long carId) {
        Car car = service.getById(carId);
        car.setStatus(CarStatus.VACANT);
        service.save(car);
    }
}
