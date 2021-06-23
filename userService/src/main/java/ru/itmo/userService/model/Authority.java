package ru.itmo.userService.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Authority {
    USER_READ("user:read"),
    USER_WRITE("user:write");

    private final String authority;
}
