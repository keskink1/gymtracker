package com.keskin.workouts.service.impl;

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

    private Workout findWorkout(Long id) {
        return workoutRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Workout not found!"));
    }

    @Override
    public WorkoutDto getWorkout(Long id) {
        Workout workout = findWorkout(id);
        return workoutMapper.entityToDto(workout);
    }

    @Override
    public List<WorkoutDto> getAll() {
        return workoutRepository.findAll()
                .stream()
                .map(workout -> workoutMapper.entityToDto(workout))
                .toList();
    }

    @Override
    public WorkoutDto createWorkout(CreateWorkoutRequestDto requestDto) {
        Workout workout = workoutMapper.createRequestToEntity(requestDto);

        if (workout.getItems() != null) { // let childrne know which workout they belong to
            workout.getItems().forEach(item -> item.setWorkout(workout));
        }
        Workout savedWorkout = workoutRepository.save(workout);
        return workoutMapper.entityToDto(savedWorkout);
    }

    @Override
    public WorkoutDto updateWorkout(Long id, UpdateWorkoutRequestDto requestDto) {
        Workout existingWorkout = workoutRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Workout not found"));
        workoutMapper.updateRequestToEntity(requestDto, existingWorkout);

        Workout savedWorkout = workoutRepository.save(existingWorkout);

        return workoutMapper.entityToDto(savedWorkout);
    }

    @Override
    public void deleteWorkout(Long id) {
        Workout workout = findWorkout(id);
        workoutRepository.delete(workout);
    }
}
