package ru.itmo.userService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.userService.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

}
