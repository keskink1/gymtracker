package com.keskin.workouts.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "workout_items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkoutItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_id")
    private Workout workout;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "local_exercise_id")
    private LocalExercise exercise;

    @Column(name = "sets")
    private int sets;

    @Column(name = "reps")
    private int reps;

    @Column(name = "weight")
    private double weight;
}