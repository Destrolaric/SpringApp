package ru.itmo.tripService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trip")
public class TestRest {

    @GetMapping("/hello")
    public String hello() {
        return "Hello from trip service!";
    }
}
