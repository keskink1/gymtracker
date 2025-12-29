package com.keskin.users.dto.request;

import jakarta.validation.constraints.Size;

public record UpdateUserRequestDto(
        @Size(min = 3, message = "First name must be at least 3 characters")
        String firstName,

        @Size(min = 3, message = "Last name must be at least 3 characters")
        String lastName,

        Double height,
        Double weight,
        @Size(min = 18, max = 90)
        Integer age
) {}