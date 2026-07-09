package com.katllv.scorekeeper_be.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.katllv.scorekeeper_be.user.User;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secret;

    private static final long EXPIRATION_MILLIS = 1000 * 60 * 60; // 1 hour

    public String generateAccessToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        String token = JWT.create()
                .withSubject(user.getUsername())
                .withClaim("userId", user.getId().toString())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_MILLIS))
                .sign(algorithm);
        return token;
    }

    // public void validateAccessToken(String token) {
    //     Algorithm algorithm = Algorithm.HMAC256(secret);
    //     JWT.require(algorithm)
    //             .build()
    //             .verify(token)
    //             .getSubject();
    // }
}
