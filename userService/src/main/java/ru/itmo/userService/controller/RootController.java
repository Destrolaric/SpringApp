package ru.itmo.userService.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class RootController {

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password) {
        return email + " " + password;
    }

    @PostMapping("/signup")
    public String signup(@RequestParam String email,
                         @RequestParam String password) {
        return email + " " + password;
    }

    @PostMapping("/password-update")
    public String passwordUpdate(@RequestParam String new_password) {
        return new_password;
    }
}
