package com.katllv.scorekeeper_be.security;

import com.katllv.scorekeeper_be.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    private static final long EXPIRATION_DAYS = 7;

    public RefreshToken generateRefreshToken(User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiresAt(Instant.now().plus(EXPIRATION_DAYS, ChronoUnit.DAYS));

        return refreshTokenRepository.save(refreshToken);
    } 

    public RefreshToken validateRefreshToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid refresh token"));

        if (refreshToken.getExpiresAt().isBefore(Instant.now())) {
            //delete expired token from database
            refreshTokenRepository.delete(refreshToken);
            //throw exception for expired token
            throw new IllegalArgumentException("Refresh token expired");
    }

    return refreshToken;
}

}
