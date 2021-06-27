package ru.itmo.tripService.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itmo.tripService.model.Car;

@FeignClient("car-service")
@RequestMapping("/car")
public interface CarFeignClient {

    @GetMapping("/findNearest")
    Car findNearestCar(@RequestParam Double latitude,
                       @RequestParam Double longitude);

    @PostMapping("/finishTrip")
    void finish(@RequestParam Integer carId);
}
