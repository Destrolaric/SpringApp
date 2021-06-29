package ru.itmo.userService.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itmo.userService.model.Role;
import ru.itmo.userService.model.User;
import ru.itmo.userService.repository.UserRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public User createUser(User user) {
        if (getByUsername(user.getUsername()) != null) {
            throw new RuntimeException();
        }
        user.setRole(Role.USER);
        user.setEnabled(true);
        return userRepo.save(user);
    }

    public User getById(Long id) {
        return userRepo.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    public User getByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public List<User> getUsers() {
        return userRepo.findAll();
    }

    public boolean checkPassword(User user, String password) {
        return passwordEncoder.matches(password, user.getPassword());
    }

    public void updatePassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        userRepo.save(user);
    }

}
