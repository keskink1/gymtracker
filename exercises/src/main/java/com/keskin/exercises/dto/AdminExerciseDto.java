package com.keskin.exercises.dto;


public record AdminExerciseDto(
        Long id,
        String name,
        String description,
        String muscleGroup,
        String userEmail
) {
}

