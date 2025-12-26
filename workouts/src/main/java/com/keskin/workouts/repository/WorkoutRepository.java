package com.keskin.workouts.repository;

import com.keskin.workouts.entity.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {
  }