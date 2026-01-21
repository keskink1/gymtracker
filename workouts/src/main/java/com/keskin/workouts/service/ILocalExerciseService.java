package com.keskin.workouts.service;

import com.keskin.workouts.dto.message.ExerciseEventDto;

public interface ILocalExerciseService {
    void saveOrUpdateExercise(ExerciseEventDto event);
}
