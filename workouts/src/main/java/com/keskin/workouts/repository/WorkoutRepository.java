package com.keskin.workouts.repository;

import com.keskin.workouts.entity.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    List<Workout> findAllByUserEmail(String userEmail);

    Optional<Workout> findByIdAndUserEmail(Long id, String userEmail);}