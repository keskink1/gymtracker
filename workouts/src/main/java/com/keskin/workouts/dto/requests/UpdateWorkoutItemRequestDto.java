package com.keskin.workouts.dto.requests;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record UpdateWorkoutItemRequestDto(
        @Positive(message = "Sets must be greater than 0")
        Integer sets, //Integer, otherwise if value is null in front it becomes 0

        @Positive(message = "Reps must be greater than 0")
        Integer reps,

        @PositiveOrZero(message = "Weight cannot be negative")
        Double weight
) {
}