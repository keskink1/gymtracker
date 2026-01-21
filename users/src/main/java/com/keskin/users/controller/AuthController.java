package com.keskin.users.controller;

import com.keskin.users.dto.UserDto;
import com.keskin.users.dto.request.LoginRequestDto;
import com.keskin.users.dto.request.RefreshTokenRequest;
import com.keskin.users.dto.request.RegisterRequestDto;
import com.keskin.users.dto.response.AuthResponse;
import com.keskin.users.service.IAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody RegisterRequestDto request) {
        UserDto savedUser = authService.register(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/users/{id}")
                .buildAndExpand(savedUser.id())
                .toUri();

        return ResponseEntity.created(location).body(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequestDto request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestBody RefreshTokenRequest request) {
        AuthResponse response = authService.refreshToken(request.refreshToken());
        return ResponseEntity.ok(response);
    }
}