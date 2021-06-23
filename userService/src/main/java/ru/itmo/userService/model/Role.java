package ru.itmo.userService.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum Role {
    USER(Arrays.asList(Authority.USER_READ)),
    ADMIN(Arrays.asList(Authority.USER_READ, Authority.USER_WRITE));

    private final List<Authority> authorities;

    public List<SimpleGrantedAuthority> getAuthorities() {
        return getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
                .collect(Collectors.toList());
    }
}
