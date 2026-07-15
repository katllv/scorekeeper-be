package com.katllv.scorekeeper_be.user;
import com.katllv.scorekeeper_be.user.dto.AuthResponse;

import com.katllv.scorekeeper_be.security.JwtService;
import com.katllv.scorekeeper_be.security.RefreshToken;
import com.katllv.scorekeeper_be.security.RefreshTokenService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

// responsible for user-related operations, such as registration and login
@Service
@RequiredArgsConstructor // generates a constructor with required arguments for all final fields, allowing for dependency injection of the services and repository
public class UserService {

    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(String username, String rawPassword, String displayName) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        String hashedPassword = passwordEncoder.encode(rawPassword);

        User user = new User();
        user.setUsername(username);
        user.setPasswordHash(hashedPassword);
        user.setDisplayName(displayName);

        return userRepository.save(user);
    }

    public AuthResponse login(String username, String rawPassword) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

        if (!passwordEncoder.matches(rawPassword, user.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        String accessToken = jwtService.generateAccessToken(user);
        RefreshToken refreshToken = refreshTokenService.generateRefreshToken(user);

        return new AuthResponse(accessToken, refreshToken.getToken());
    }
}
