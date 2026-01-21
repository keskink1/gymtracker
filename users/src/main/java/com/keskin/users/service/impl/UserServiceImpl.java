package com.keskin.users.service.impl;

import com.keskin.users.config.UserContextHolder;
import com.keskin.users.dto.request.UpdateUserRequestDto;
import com.keskin.users.dto.UserDto;
import com.keskin.users.entity.User;
import com.keskin.users.mapper.UserMapper;
import com.keskin.users.repository.UserRepository;
import com.keskin.users.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    private User findUser(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("User with the id not found: " + id));
    }

    /**
     * Private method for centralized managing
     */
    private void validateOwnershipOrAdmin(User user) {
        boolean isOwner = user.getEmail().equals(UserContextHolder.getEmail());
        boolean isAdmin = UserContextHolder.isAdmin();

        if (!isOwner && !isAdmin) {
            throw new AccessDeniedException("You don't have permission for this action!");
        }
    }


    @Override
    public UserDto getUser(Long id) {
        User user = findUser(id);
        validateOwnershipOrAdmin(user);
        return userMapper.entityToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        if (!UserContextHolder.isAdmin()) {
            throw new AccessDeniedException("Only admins can list all users!");
        }
        return userRepository.findAll()
                .stream()
                .map(userMapper::entityToDto)
                .toList();
    }

    @Override
    public UserDto updateUser(Long id, UpdateUserRequestDto requestDto) {
        User user = findUser(id);
        validateOwnershipOrAdmin(user);

        //prevent email from overriding
        String originalEmail = user.getEmail();
        userMapper.updateRequestToEntity(requestDto, user);
        user.setEmail(originalEmail);

        return userMapper.entityToDto(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        User user = findUser(id);
        validateOwnershipOrAdmin(user);
        userRepository.delete(user);
    }

    @Override
    public void toggleActive(Long id) {
        User user = findUser(id);
        validateOwnershipOrAdmin(user);

        user.setActive(!user.getActive());
        userRepository.save(user);
    }
}