package ru.itmo.tripService.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("car-service")
@RequestMapping("/car")
public interface CarFeignClient {

    @GetMapping("/findNearest")
    Long findNearestCar(@RequestParam Double latitude,
                        @RequestParam Double longitude);

    @PostMapping("/finishTrip")
    void finish(@RequestParam Long carId);
}
