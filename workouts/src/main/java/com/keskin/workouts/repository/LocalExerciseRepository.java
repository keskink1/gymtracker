package com.keskin.workouts.repository;

import com.keskin.workouts.entity.LocalExercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalExerciseRepository extends JpaRepository<LocalExercise, Long> {
}