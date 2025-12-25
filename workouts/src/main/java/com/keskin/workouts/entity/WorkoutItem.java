package com.keskin.workouts.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkoutItem {
    private Long id;
    private Long workoutId;
    private Long exerciseId;
    private int sets;
    private int reps;
    private double weight;
}