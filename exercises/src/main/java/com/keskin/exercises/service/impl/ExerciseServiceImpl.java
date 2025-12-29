package com.keskin.exercises.service.impl;

import com.keskin.exercises.dto.ExerciseDto;
import com.keskin.exercises.dto.request.CreateExerciseRequestDto;
import com.keskin.exercises.dto.request.UpdateExerciseRequestDto;
import com.keskin.exercises.entity.Exercise;
import com.keskin.exercises.exception.ResourceAlreadyExistsException;
import com.keskin.exercises.mapper.ExerciseMapper;
import com.keskin.exercises.repository.ExerciseRepository;
import com.keskin.exercises.service.IExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class ExerciseServiceImpl implements IExerciseService {

    private final ExerciseMapper exerciseMapper;
    private final ExerciseRepository exerciseRepository;

    private Exercise findExercise(Long id) {
        return exerciseRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Exercise not found!"));
    }

    @Override
    public List<ExerciseDto> getAll() {
        return exerciseRepository.findAll().stream()
                .map(exerciseMapper::entityToDto)
                .toList();
    }

    @Override
    public ExerciseDto getExercise(Long id) {
        Exercise exercise = findExercise(id);
        return exerciseMapper.entityToDto(exercise);
    }

    @Override
    public ExerciseDto createExercise(CreateExerciseRequestDto requestDto) {
        exerciseRepository.findByName(requestDto.name()).ifPresent(exercise -> {
            throw new ResourceAlreadyExistsException("Name already exists : " + requestDto.name());
        });

        Exercise newExercise = exerciseMapper.createRequestToEntity(requestDto);
        Exercise savedExercise = exerciseRepository.save(newExercise);

        return exerciseMapper.entityToDto(savedExercise);
    }

    @Override
    public ExerciseDto updateExercise(UpdateExerciseRequestDto requestDto, Long id) {
        Exercise exercise = findExercise(id);

        if (requestDto.name() != null && !requestDto.name().equals(exercise.getName())) {
            exerciseRepository.findByName(requestDto.name()).ifPresent(requestExercise -> {
                throw new ResourceAlreadyExistsException("Exercise with this name already exists!");
            });
        }
        exerciseMapper.updateRequestToEntity(requestDto, exercise);

        Exercise updatedExercise = exerciseRepository.save(exercise);
        return exerciseMapper.entityToDto(updatedExercise);
    }

    @Override
    public void deleteExercise(Long id) {
        Exercise exercise = findExercise(id);
        exerciseRepository.delete(exercise);
    }
}
