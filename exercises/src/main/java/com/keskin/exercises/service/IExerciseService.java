package com.keskin.exercises.service;

import com.keskin.exercises.dto.AdminExerciseDto;
import com.keskin.exercises.dto.ExerciseDto;
import com.keskin.exercises.dto.request.CreateExerciseRequestDto;
import com.keskin.exercises.dto.request.UpdateExerciseRequestDto;

import java.util.List;

public interface IExerciseService {
    List<ExerciseDto> getAll();

    ExerciseDto getExercise(Long id);

    List<AdminExerciseDto> getAllForAdmin();

    AdminExerciseDto getExerciseForAdmin(Long id);

    ExerciseDto createExercise(CreateExerciseRequestDto requestDto);

    ExerciseDto updateExercise(UpdateExerciseRequestDto requestDto, Long id);
    void deleteExercise(Long id);
}
