package ru.itmo.userService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.userService.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    Optional<User> getByToken(String token);

}
