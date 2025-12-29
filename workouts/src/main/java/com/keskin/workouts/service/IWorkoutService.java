package com.keskin.workouts.service;

import com.keskin.workouts.dto.requests.CreateWorkoutRequestDto;
import com.keskin.workouts.dto.requests.UpdateWorkoutRequestDto;
import com.keskin.workouts.dto.WorkoutDto;

import java.util.List;

public interface IWorkoutService {
    WorkoutDto getWorkout(Long id);

    List<WorkoutDto> getAll();

    WorkoutDto createWorkout(CreateWorkoutRequestDto requestDto);

    WorkoutDto updateWorkout(Long id, UpdateWorkoutRequestDto requestDto);

    void deleteWorkout(Long id);


}
