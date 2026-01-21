package com.keskin.workouts.dto.message;

public record ExerciseCreatedEventDto(
        Long id,
        String name,
        String muscleGroup
) {}