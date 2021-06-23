package ru.itmo.userService.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.userService.model.Role;
import ru.itmo.userService.repository.UserRepository;
import ru.itmo.userService.model.User;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepo;

    public User createUser(User user) {
        if (getByUsername(user.getUsername()) != null) {
            throw new RuntimeException();
        }
        user.setRole(Role.USER);
        user.setEnabled(true);
        return userRepo.save(user);
    }

    public User getById(Long id) {
        return userRepo.findById(id).get();
    }

    public User getByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public List<User> getUsers() {
        return userRepo.findAll();
    }

}
