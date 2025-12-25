package com.keskin.workouts.repository;

import com.keskin.workouts.entity.Workout;

import java.util.List;

public interface IWorkoutRepository {
    void save(Workout workout);

    List<Workout> findAll();

    Workout findById(Long id);

    void update(Workout workout);

    void delete(Long id);
}
