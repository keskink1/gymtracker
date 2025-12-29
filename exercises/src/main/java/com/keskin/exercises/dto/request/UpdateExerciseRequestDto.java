package com.keskin.exercises.dto.request;

import jakarta.validation.constraints.Size;

public record UpdateExerciseRequestDto(
        @Size(min = 2, max = 50)
        String name,
        String muscleGroup,
        @Size(max = 255)
        String description
) {}