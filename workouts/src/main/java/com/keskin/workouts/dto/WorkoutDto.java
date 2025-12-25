package com.keskin.workouts.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

public record WorkoutDto(
        @JsonIgnore
        Long id,
        @JsonIgnore
        Long userId,
        String workoutName,
        String category,
        LocalDateTime createdAt
) {
}
