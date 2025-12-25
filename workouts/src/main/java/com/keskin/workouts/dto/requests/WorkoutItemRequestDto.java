package com.keskin.workouts.dto.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record WorkoutItemRequestDto(
        @NotNull(message = "Exercise ID is required")
        Long exerciseId,

        @Min(value = 1, message = "Sets must be at least 1")
        int sets,

        @Min(value = 1, message = "Reps must be at least 1")
        int reps,

        double weight
) {}