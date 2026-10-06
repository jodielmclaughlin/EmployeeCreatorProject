package io.nology.project.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.nology.project.auth.entity.AppUser;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtService {
    private static final Duration ACCESS_TOKEN_TTL = Duration.ofMinutes(15);

    @Value("${app.jwt.secret}")
    private String secret;
    private SecretKey key;

    @PostConstruct
    public void init(){
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        if (keyBytes.length < 32){
            throw new IllegalStateException(
                    "app.jwt.secret must be at least 256bit (32 bytes) currently " + keyBytes.length + " bytes"
            );
        }
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateAccessToken(AppUser user){
        Instant now = Instant.now();

        return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .subject(user.getId().toString())
                .claim("role", user.getRole().name())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(ACCESS_TOKEN_TTL)))
                .signWith(key)
                .compact();
    }

    public Jws<Claims> parse(String token){
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
    }
}
