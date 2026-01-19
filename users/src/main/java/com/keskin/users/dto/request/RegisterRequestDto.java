package com.keskin.users.dto.request;

import jakarta.validation.constraints.*;

public record RegisterRequestDto(
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

        @NotBlank(message = "Password confirmation cannot be blank")
        String confirmPassword, // İkinci şifre kontrolü için

        @Min(value = 1, message = "Height must be positive")
        Double height,

        @Min(value = 1, message = "Weight must be positive")
        Double weight,

        @Min(value = 18, message = "Minimum age is 18")
        @Max(value = 100, message = "Maximum age is 100")
        Integer age
) {}