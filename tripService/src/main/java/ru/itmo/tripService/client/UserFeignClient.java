package ru.itmo.tripService.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("user-service")
@RequestMapping("/api/v1/user")
public interface UserFeignClient {

    @GetMapping("/approve-token")
    Long approveToken(@RequestHeader("Authorization") String token);
}
