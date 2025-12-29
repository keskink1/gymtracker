package com.keskin.exercises.service;

import com.keskin.exercises.dto.ExerciseDto;
import com.keskin.exercises.dto.request.CreateExerciseRequestDto;
import com.keskin.exercises.dto.request.UpdateExerciseRequestDto;
import com.keskin.exercises.entity.Exercise;

import java.util.List;

public interface IExerciseService {
    List<ExerciseDto> getAll();

    ExerciseDto getExercise(Long id);

    ExerciseDto createExercise(CreateExerciseRequestDto requestDto);

    ExerciseDto updateExercise(UpdateExerciseRequestDto requestDto, Long id);
    void deleteExercise(Long id);
}
