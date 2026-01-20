package com.keskin.users.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record UpdateUserRequestDto(
        @Size(min = 3, message = "First name must be at least 3 characters")
        String firstName,

        @Size(min = 3, message = "Last name must be at least 3 characters")
        String lastName,

        @Min(value = 30, message = "Height must be at least 30 cm")
        Double height,

        @Min(value = 2, message = "Weight must be at least 2 kg")
        Double weight,

        @Min(value = 18, message = "Age must be at least 18")
        @Max(value = 90, message = "Age must be at most 90")
        Integer age
) {}