package com.keskin.users.service;

import com.keskin.users.dto.request.CreateUserRequestDto;
import com.keskin.users.dto.request.UpdateUserRequestDto;
import com.keskin.users.dto.UserDto;

import java.util.List;

public interface IUserService {

    UserDto getUser(Long id);

    List<UserDto> getAllUsers();

    UserDto createUser(CreateUserRequestDto requestDto);

    UserDto updateUser(Long id, UpdateUserRequestDto requestDto);

    void deleteUser(Long id);


    void toggleActive(Long id);
}


