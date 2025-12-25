package com.keskin.users.service;

import com.keskin.users.dto.CreateUserRequestDto;
import com.keskin.users.dto.UpdateUserRequestDto;
import com.keskin.users.dto.UserDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

public interface IUserService {

    UserDto getUser(Long id);

    List<UserDto> getAllUsers();

    UserDto createUser(CreateUserRequestDto requestDto);

    UserDto updateUser(Long id, UpdateUserRequestDto requestDto);

    void deleteUser(Long id);


    void toggleActive(Long id);
}


