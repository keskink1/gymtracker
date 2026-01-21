package com.keskin.workouts.service;

import com.keskin.workouts.dto.message.ExerciseCreatedEventDto;

public interface ILocalExerciseService {
    void saveOrUpdateExercise(ExerciseCreatedEventDto event);

    void softDeleteLocalExercise(Long id);
}
