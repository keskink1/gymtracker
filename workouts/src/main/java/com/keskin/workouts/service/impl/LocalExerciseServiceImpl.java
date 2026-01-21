package com.keskin.workouts.service.impl;

import com.keskin.workouts.dto.message.ExerciseEventDto;
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
    public void saveOrUpdateExercise(ExerciseEventDto event) {
        LocalExercise localExercise = LocalExercise.builder()
                .id(event.getId())
                .name(event.getName())
                .muscleGroup(event.getMuscleGroup())
                .build();

        localExerciseRepository.save(localExercise);
        log.info("Exercise saved! ID: {}", event.getId());
    }
}
