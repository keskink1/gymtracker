package com.keskin.exercises.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateExerciseRequestDto(
        @NotBlank(message = "Exercise name cannot be empty")
        @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
        String name,

        @NotBlank(message = "Muscle group cannot be empty")
        String muscleGroup,

        @Size(max = 255, message = "Description can be maximum 255 characters")
        String description
) {
}