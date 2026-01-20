package com.keskin.exercises.service.impl;

import com.keskin.exercises.config.UserContextHolder;
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

    private Exercise findAndValidate(Long id) {
        if (UserContextHolder.isAdmin()) {
            return exerciseRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Exercise not found!"));
        }

        String email = UserContextHolder.getEmail().trim();
        return exerciseRepository.findByIdAndUserEmail(id, email)
                .orElseThrow(() -> new IllegalArgumentException("Exercise not found!"));
    }

    @Override
    public List<ExerciseDto> getAll() {
        if (UserContextHolder.isAdmin()) {
            return exerciseRepository.findAll()
                    .stream()
                    .map(exerciseMapper::entityToDto)
                    .toList();
        }

        String email = UserContextHolder.getEmail();
        if (email == null || email.isBlank()) return List.of();

        return exerciseRepository.findAllByUserEmail(email.trim())
                .stream()
                .map(exerciseMapper::entityToDto)
                .toList();
    }

    @Override
    public ExerciseDto getExercise(Long id) {
        Exercise exercise = findAndValidate(id);
        return exerciseMapper.entityToDto(exercise);
    }

    @Override
    public ExerciseDto createExercise(CreateExerciseRequestDto requestDto) {
        exerciseRepository.findByName(requestDto.name()).ifPresent(e -> {
            throw new ResourceAlreadyExistsException("Name already exists: " + requestDto.name());
        });

        Exercise newExercise = exerciseMapper.createRequestToEntity(requestDto);
        newExercise.setUserEmail(UserContextHolder.getEmail());

        return exerciseMapper.entityToDto(exerciseRepository.save(newExercise));
    }

    @Override
    public ExerciseDto updateExercise(UpdateExerciseRequestDto requestDto, Long id) {
        Exercise exercise = findAndValidate(id);

        if (requestDto.name() != null && !requestDto.name().equals(exercise.getName())) {
            exerciseRepository.findByName(requestDto.name()).ifPresent(e -> {
                throw new ResourceAlreadyExistsException("Exercise with this name already exists!");
            });
        }

        exerciseMapper.updateRequestToEntity(requestDto, exercise);

        if (!UserContextHolder.isAdmin()) {
            exercise.setUserEmail(UserContextHolder.getEmail());
        }

        return exerciseMapper.entityToDto(exerciseRepository.save(exercise));
    }

    @Override
    public void deleteExercise(Long id) {
        Exercise exercise = findAndValidate(id);
        exerciseRepository.delete(exercise);
    }
}