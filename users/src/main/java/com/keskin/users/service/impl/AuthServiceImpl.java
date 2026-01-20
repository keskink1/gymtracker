package com.keskin.users.service.impl;

import com.keskin.users.dto.UserDto;
import com.keskin.users.dto.request.LoginRequestDto;
import com.keskin.users.dto.request.RegisterRequestDto;
import com.keskin.users.entity.Role;
import com.keskin.users.entity.User;
import com.keskin.users.exception.ResourceAlreadyExistsException;
import com.keskin.users.jwt.JwtService;
import com.keskin.users.mapper.UserMapper;
import com.keskin.users.repository.UserRepository;
import com.keskin.users.service.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class AuthServiceImpl implements IAuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public UserDto register(RegisterRequestDto request) {
        if (!request.password().equals(request.confirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match!");
        }

        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new ResourceAlreadyExistsException("Email already in use!");
        }

        User user = userMapper.createRequestToEntity(request);
        user.setRole(Role.USER);
        user.setActive(true);

        String encodedPassword = passwordEncoder.encode(request.password());
        user.setPassword(encodedPassword);

        userRepository.save(user);

        return userMapper.entityToDto(user);
    }

    @Override
    public String login(LoginRequestDto request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("User not found!"));

        if (passwordEncoder.matches(request.password(), user.getPassword())) {
            return jwtService.createToken(user.getEmail(), user.getRole().name());
        } else {
            throw new RuntimeException("Wrong password!");
        }
    }
}