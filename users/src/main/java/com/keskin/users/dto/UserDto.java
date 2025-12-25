package com.keskin.users.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record UserDto(
        @JsonIgnore
        Long id,
        String firstName,
        String lastName,
        String email,
        Double height,
        Double weight,
        Integer age,
        boolean isActive
) {
}
