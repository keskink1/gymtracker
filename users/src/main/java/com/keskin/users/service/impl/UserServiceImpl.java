package com.keskin.users.service.impl;

import com.keskin.users.dto.CreateUserRequestDto;
import com.keskin.users.dto.UpdateUserRequestDto;
import com.keskin.users.dto.UserDto;
import com.keskin.users.entity.User;
import com.keskin.users.mapper.UserMapper;
import com.keskin.users.repository.UserRepository;
import com.keskin.users.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private User findUser(Long id){
        return userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("User with the id not found : " + id));
    }

    @Override
    public UserDto getUser(Long id) {
        User user = findUser(id);
        return userMapper.entityToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> userMapper.entityToDto(user))
                .toList();
    }

    @Override
    public UserDto createUser(CreateUserRequestDto requestDto) {
        Optional<User> existingUser = userRepository.findByEmail(requestDto.email());

        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Email " + requestDto.email() + " already exists");
        }

            User newUser = userMapper.createRequestToEntity(requestDto);
            User savedUser = userRepository.save(newUser);
            return userMapper.entityToDto(savedUser);
    }

    @Override
    public UserDto updateUser(Long id, UpdateUserRequestDto requestDto) {
        User user = findUser(id);
        User updatedUser = userMapper.updateRequestToEntity(requestDto, user);
        userRepository.save(updatedUser);
        return userMapper.entityToDto(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        User user = findUser(id);
        userRepository.delete(user);
    }

    @Override
    public void toggleActive(Long id) {
        User user = findUser(id);
        user.setActive(!user.isActive());
        userRepository.save(user);
    }
}
