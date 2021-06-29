package ru.itmo.userService.controller.v1;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.itmo.userService.dto.UserLoginDTO;
import ru.itmo.userService.dto.UserPasswordUpdateDTO;
import ru.itmo.userService.dto.UserRegistrationDTO;
import ru.itmo.userService.model.User;
import ru.itmo.userService.service.TokenService;
import ru.itmo.userService.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserControllerV1 {

    private final UserService userService;
    private final TokenService tokenService;
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
    public String login(@RequestBody @Valid UserLoginDTO userLoginDTO) {
        return tokenService.getToken(userLoginDTO.getUsername(), userLoginDTO.getPassword());
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public UserRegistrationDTO register(@RequestBody @Valid UserRegistrationDTO userRegistrationDTO) {
        User user = convertToEntity(userRegistrationDTO);
        User userCreated = userService.createUser(user);
        UserRegistrationDTO userDTO = convertToDto(userCreated);
        userDTO.setToken(tokenService.getToken(user.getUsername(), user.getPassword()));
        return userDTO;
    }

    @PostMapping("/password-update")
    public void passwordUpdate(@RequestBody @Valid UserPasswordUpdateDTO userPasswordUpdateDTO) {
        User user = userService.getByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if (!userService.checkPassword(user, userPasswordUpdateDTO.getOldPassword())) {
            throw new RuntimeException("Wrong password");
        }
        userService.updatePassword(user, userPasswordUpdateDTO.getNewPassword());
    }

    @GetMapping("/approve-token")
    public Long approveToken(@RequestHeader("Authorization") String token) {
        return tokenService.getId(token.replace("Bearer", ""));
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
