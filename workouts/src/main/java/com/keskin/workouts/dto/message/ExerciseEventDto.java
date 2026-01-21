package com.keskin.workouts.dto.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseEventDto {
    private Long id;
    private String name;
    private String muscleGroup;
}
