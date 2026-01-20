package com.keskin.workouts.service.impl;

import com.keskin.workouts.config.UserContextHolder;
import com.keskin.workouts.dto.requests.CreateWorkoutRequestDto;
import com.keskin.workouts.dto.requests.UpdateWorkoutRequestDto;
import com.keskin.workouts.dto.WorkoutDto;
import com.keskin.workouts.entity.Workout;
import com.keskin.workouts.mapper.WorkoutMapper;
import com.keskin.workouts.repository.WorkoutRepository;
import com.keskin.workouts.service.IWorkoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkoutServiceImpl implements IWorkoutService {

    private final WorkoutRepository workoutRepository;
    private final WorkoutMapper workoutMapper;

    private Workout findAndValidate(Long id) {
        if (UserContextHolder.isAdmin()) {
            return workoutRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Workout not found!"));
        }

        String email = UserContextHolder.getEmail().trim();
        return workoutRepository.findByIdAndUserEmail(id, email)
                .orElseThrow(() -> new IllegalArgumentException("Workout not found!"));
    }

    @Override
    public WorkoutDto getWorkout(Long id) {
        Workout workout = findAndValidate(id);
        return workoutMapper.entityToDto(workout);
    }

    @Override
    public List<WorkoutDto> getAll() {
        if (UserContextHolder.isAdmin()) {
            return workoutRepository.findAll()
                    .stream()
                    .map(workoutMapper::entityToDto)
                    .toList();
        }

        String email = UserContextHolder.getEmail();
        if (email == null || email.isBlank()) return List.of();

        return workoutRepository.findAllByUserEmail(email.trim())
                .stream()
                .map(workoutMapper::entityToDto)
                .toList();
    }

    @Override
    public WorkoutDto createWorkout(CreateWorkoutRequestDto requestDto) {
        Workout workout = workoutMapper.createRequestToEntity(requestDto);
        workout.setUserEmail(UserContextHolder.getEmail());

        if (workout.getItems() != null) {
            workout.getItems().forEach(item -> item.setWorkout(workout));
        }

        return workoutMapper.entityToDto(workoutRepository.save(workout));
    }

    @Override
    public WorkoutDto updateWorkout(Long id, UpdateWorkoutRequestDto requestDto) {
        Workout exercise = findAndValidate(id);
        workoutMapper.updateRequestToEntity(requestDto, exercise);

        if (!UserContextHolder.isAdmin()) {
            exercise.setUserEmail(UserContextHolder.getEmail());
        }

        if (exercise.getItems() != null) {
            exercise.getItems().forEach(item -> item.setWorkout(exercise));
        }

        return workoutMapper.entityToDto(workoutRepository.save(exercise));
    }

    @Override
    public void deleteWorkout(Long id) {
        Workout workout = findAndValidate(id);
        workoutRepository.delete(workout);
    }
}