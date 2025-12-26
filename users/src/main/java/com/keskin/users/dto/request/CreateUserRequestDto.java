package com.keskin.users.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserRequestDto(
        @NotBlank(message = "First name cannot be blank")
        @Size(min = 3, message = "First name must be at least 3 characters")
        String firstName,

        @NotBlank(message = "Last name cannot be blank")
        @Size(min = 3, message = "Last name must be at least 3 characters")
        String lastName,

        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Email should be valid")
        String email,

        @NotBlank(message = "Password cannot be blank")
        @Size(min = 4, message = "Password must be at least 4 characters")
        String password,

        Double height,
        Double weight,
        Integer age
) {}