package com.keskin.workouts.entity;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Workout {
    private Long id;
    private Long userId;
    private String workoutName;
    private String category;
    private LocalDateTime createdAt;
    private List<WorkoutItem> items;
}