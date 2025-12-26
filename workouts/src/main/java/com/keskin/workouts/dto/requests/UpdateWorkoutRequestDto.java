package com.keskin.workouts.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record UpdateWorkoutRequestDto(
        @NotBlank(message = "Workout name cannot be empty")
        @Size(min = 3, message = "Workout name must be at least 3 characters long")
        String workoutName,

        @NotBlank(message = "Category cannot be empty")
        @Size(min = 3, message = "Category must be at least 3 characters long")
        String category,

        List<CreateWorkoutItemRequestDto> items
) {
}