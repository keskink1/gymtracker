package com.keskin.exercises.dto.message;

public record ExerciseCreatedEventDto(
        Long id,
        String name,
        String muscleGroup
) {}