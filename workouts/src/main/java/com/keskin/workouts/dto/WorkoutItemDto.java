package com.keskin.workouts.dto;

public record WorkoutItemDto(
        Long id,
        Long exerciseId,
        String exerciseName,
        int sets,
        int reps,
        double weight
) {}