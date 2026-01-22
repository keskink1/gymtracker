package com.keskin.users.service;

import com.keskin.users.dto.UserDto;
import com.keskin.users.dto.request.LoginRequestDto;
import com.keskin.users.dto.request.RegisterRequestDto;
import com.keskin.users.dto.response.AuthResponse;

public interface IAuthService {
    UserDto register(RegisterRequestDto request);

    AuthResponse login(LoginRequestDto request);

    AuthResponse refreshToken(String refreshToken);

    void logout(String email);
}
