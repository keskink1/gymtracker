package com.keskin.exercises.repository;

import com.keskin.exercises.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    Optional<Exercise> findByName(String name);

    List<Exercise> findAllByUserEmail(String userEmail);

    Optional<Exercise> findByIdAndUserEmail(Long id, String userEmail);

    Optional<Exercise> findByNameAndUserEmail(String name, String userEmail);
}