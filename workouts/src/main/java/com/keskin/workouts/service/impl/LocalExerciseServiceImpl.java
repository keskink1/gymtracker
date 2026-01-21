package com.keskin.workouts.service.impl;

import com.keskin.workouts.dto.message.ExerciseCreatedEventDto;
import com.keskin.workouts.dto.message.ExerciseDeletedEventDto;
import com.keskin.workouts.entity.LocalExercise;
import com.keskin.workouts.repository.LocalExerciseRepository;
import com.keskin.workouts.service.ILocalExerciseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class LocalExerciseServiceImpl implements ILocalExerciseService {


    private final LocalExerciseRepository localExerciseRepository;

    @Override
    public void saveOrUpdateExercise(ExerciseCreatedEventDto event) {
        LocalExercise localExercise = localExerciseRepository.findById(event.id())
                .orElse(new LocalExercise());

        localExercise.setId(event.id());
        localExercise.setName(event.name());
        localExercise.setMuscleGroup(event.muscleGroup());
        localExercise.setActive(true);

        localExerciseRepository.save(localExercise);
        log.info("Local exercise synced! ID: {}, Name: {}", event.id(), event.name());
    }

    @Transactional
    public void softDeleteLocalExercise(Long id) {
        localExerciseRepository.findById(id).ifPresent(exercise -> {
            exercise.setActive(false);
            localExerciseRepository.save(exercise);
            log.info("Exercise marked as inactive. ID: {}", id);
        });
    }
}
