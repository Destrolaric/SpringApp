package ru.itmo.tripService.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("user-service")
@RequestMapping("/api/v1/user")
public interface UserFeignClient {

    @GetMapping("/approve-token")
    Long approveToken(@RequestParam String token);
}
