package com.keskin.exercises.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record ExerciseDto(
        @JsonIgnore
        Long id,
        String name,
        String description,
        String muscleGroup) {
}
