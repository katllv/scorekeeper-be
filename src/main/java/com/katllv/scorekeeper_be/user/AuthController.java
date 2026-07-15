package com.katllv.scorekeeper_be.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.katllv.scorekeeper_be.user.dto.AuthResponse;
import com.katllv.scorekeeper_be.user.dto.LoginRequest;
import com.katllv.scorekeeper_be.user.dto.RegisterRequest;
import com.katllv.scorekeeper_be.user.dto.UserResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request) {
        // call userService.register(...), wrap result
        User user = userService.register(request.username(), request.password(), request.displayName());
        UserResponse response = new UserResponse(user.getId(), user.getUsername(), user.getDisplayName());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse authResponse = userService.login(request.username(), request.password());
        return ResponseEntity.ok(authResponse);
    }

}
