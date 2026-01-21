package com.keskin.workouts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "local_exercises")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LocalExercise {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "muscle_group")
    private String muscleGroup;
}