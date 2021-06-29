package ru.itmo.userService.service;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.userService.model.User;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class TokenService {

    private final UserService service;

    private static final String key = "taxi"; //idk what it have to be

    public String getToken(String username, String password) {

        User user = service.getByUsername(username);
        if (!password.equals(user.getPassword())) {
            throw new IllegalArgumentException("Unauthorized");
        }

        Map<String, Object> tokenData = new HashMap<>();
        tokenData.put("username", username);
        tokenData.put("user_id", user.getId());
        tokenData.put("role", user.getRole());
        tokenData.put("creation_time", LocalDateTime.now());

        JwtBuilder tokenBuilder = Jwts.builder();
        tokenBuilder.setClaims(tokenData);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        tokenBuilder.setExpiration(calendar.getTime());

        return tokenBuilder.signWith(SignatureAlgorithm.HS512, key).compact();
    }

    public Long getId(String token) {
        DefaultClaims claims = (DefaultClaims) Jwts.parser().setSigningKey(key).parse(token).getBody();

        return claims.get("user_id", Long.class);
    }
}
