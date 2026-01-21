package com.keskin.workouts.dto;



import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public record WorkoutDto(
        Long id,
        String userEmail,
        String workoutName,
        String category,
        List<WorkoutItemDto> items,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm:ss")
        LocalDateTime createdAt
) {}
