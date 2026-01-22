package com.keskin.workouts.service.impl;

import com.keskin.workouts.config.UserContextHolder;
import com.keskin.workouts.dto.requests.CreateWorkoutRequestDto;
import com.keskin.workouts.dto.requests.UpdateWorkoutItemRequestDto;
import com.keskin.workouts.dto.requests.UpdateWorkoutRequestDto;
import com.keskin.workouts.dto.WorkoutDto;
import com.keskin.workouts.entity.LocalExercise;
import com.keskin.workouts.entity.Workout;
import com.keskin.workouts.entity.WorkoutItem;
import com.keskin.workouts.mapper.WorkoutMapper;
import com.keskin.workouts.repository.LocalExerciseRepository;
import com.keskin.workouts.repository.WorkoutRepository;
import com.keskin.workouts.service.IWorkoutService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class WorkoutServiceImpl implements IWorkoutService {

    private final WorkoutRepository workoutRepository;
    private final LocalExerciseRepository localExerciseRepository;
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

    private LocalExercise findActiveExercise(Long id) {
        return localExerciseRepository.findById(id)
                .filter(LocalExercise::isActive)
                .orElseThrow(() -> new IllegalArgumentException("Exercise not found: " + id));
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

        if (requestDto.items() != null) {
            List<WorkoutItem> items = requestDto.items()
                    .stream()
                    .map(itemDto -> {
                        WorkoutItem item = workoutMapper.workoutItemRequestToEntity(itemDto);
                        item.setExercise(findActiveExercise(itemDto.exerciseId()));
                        item.setWorkout(workout);
                        return item;
                    }).toList();

            workout.setItems(items);
        }

        return workoutMapper.entityToDto(workoutRepository.save(workout));
    }



    @Override
    public WorkoutDto updateWorkout(Long id, UpdateWorkoutRequestDto requestDto) {
        Workout workout = findAndValidate(id);
        workoutMapper.updateRequestToEntity(requestDto, workout);

        if (!UserContextHolder.isAdmin()) {
            workout.setUserEmail(UserContextHolder.getEmail());
        }

        Workout updatedWorkout = workoutRepository.save(workout);
        return workoutMapper.entityToDto(updatedWorkout);
    }

    @Override
    public void deleteWorkout(Long id) {
        Workout workout = findAndValidate(id);
        workoutRepository.delete(workout);
    }

    @Override
    public void updateWorkoutItem(Long workoutId, Long itemId, UpdateWorkoutItemRequestDto request) {
        Workout workout = findAndValidate(workoutId);

        WorkoutItem item = workout.getItems().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("This item does not belong to this workout!"));

        workoutMapper.updateWorkoutItemFromDto(request, item);

        log.info("Item {} in Workout {} updated by {}", itemId, workoutId, UserContextHolder.getEmail());
    }
}