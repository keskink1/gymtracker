package com.keskin.workouts.service.impl;

import com.keskin.workouts.dto.requests.CreateWorkoutRequestDto;
import com.keskin.workouts.dto.requests.UpdateWorkoutRequestDto;
import com.keskin.workouts.dto.WorkoutDto;
import com.keskin.workouts.entity.Workout;
import com.keskin.workouts.mapper.WorkoutMapper;
import com.keskin.workouts.repository.IWorkoutRepository;
import com.keskin.workouts.service.IWorkoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkoutServiceImpl implements IWorkoutService {

    private final IWorkoutRepository workoutRepository;
    private final WorkoutMapper workoutMapper;

    private Workout findWorkout(Long id) {
        Workout workout =  workoutRepository.findById(id);
        if (workout == null) {
            throw  new IllegalArgumentException("Workout not found!");
        }
        return workout;
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

    }

    @Override
    public WorkoutDto updateWorkout(Long id, UpdateWorkoutRequestDto requestDto) {
        Workout workout = findWorkout(id);
        Workout updatedWorkout = workoutMapper.updateRequestToEntity(requestDto, workout);
        workoutRepository.save(updatedWorkout);
        return workoutMapper.entityToDto(updatedWorkout);
    }

    @Override
    public void deleteWorkout(Long id) {
        Workout workout = findWorkout(id);
        workoutRepository.delete(workout.getId());
    }
}
