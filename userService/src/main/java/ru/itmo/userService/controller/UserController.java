package ru.itmo.userService.controller;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.userService.dto.UserRegistrationDTO;
import ru.itmo.userService.model.User;
import ru.itmo.userService.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    @PreAuthorize("hasAuthority('user:read')")
    public List<User> getUser() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") Long id) {
        return userService.getById(id);
    }

    @PostMapping("/login")
    public String login(@RequestBody @Valid UserRegistrationDTO userRegistrationDTO) {
        return ""; //TODO
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public UserRegistrationDTO register(@RequestBody @Valid UserRegistrationDTO userRegistrationDTO) {
        User user = convertToEntity(userRegistrationDTO);
        User userCreated = userService.createUser(user);
        return convertToDto(userCreated);
    }

    @PostMapping("/password-update")
    public String passwordUpdate(@RequestParam String new_password) {
        return new_password; //TODO
    }

    private UserRegistrationDTO convertToDto(User user) {
        return modelMapper.map(user, UserRegistrationDTO.class);
    }

    private User convertToEntity(UserRegistrationDTO userRegistrationDTO) {
        User user = modelMapper.map(userRegistrationDTO, User.class);
        user.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
        return user;
    }

}
