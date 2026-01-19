package com.keskin.users.service;

import com.keskin.users.dto.UserDto;
import com.keskin.users.dto.request.LoginRequestDto;
import com.keskin.users.dto.request.RegisterRequestDto;

public interface IAuthService {
    UserDto register(RegisterRequestDto request);

     String login(LoginRequestDto request);
}
