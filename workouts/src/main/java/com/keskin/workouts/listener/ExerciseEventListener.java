package com.keskin.workouts.listener;

import com.keskin.workouts.dto.message.ExerciseEventDto;
import com.keskin.workouts.service.ILocalExerciseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ExerciseEventListener {

    private final ILocalExerciseService localExerciseService;

    @RabbitListener(queues = "exercise.queue")
    public void handleExerciseCreated(ExerciseEventDto event) {
        log.info("New exercise message receieved! Routing it to service, ID: {}, name: {}, muscle group : {}",
                event.getId(), event.getName(), event.getMuscleGroup());

        localExerciseService.saveOrUpdateExercise(event);
    }
}
