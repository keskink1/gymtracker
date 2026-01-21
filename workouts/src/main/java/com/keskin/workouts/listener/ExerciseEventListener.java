package com.keskin.workouts.listener;

import com.keskin.workouts.config.RabbitMQConfig;
import com.keskin.workouts.dto.message.ExerciseCreatedEventDto;
import com.keskin.workouts.dto.message.ExerciseDeletedEventDto;
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

    @RabbitListener(queues = RabbitMQConfig.CREATE_QUEUE)
    public void handleExerciseCreated(ExerciseCreatedEventDto event) {
        log.info("Create/Update message received for ID: {}", event.id());
        localExerciseService.saveOrUpdateExercise(event);
    }

    @RabbitListener(queues = RabbitMQConfig.DELETE_QUEUE)
    public void handleExerciseDeleted(ExerciseDeletedEventDto event) {
        log.info("Delete message received for ID: {}", event.id());
        localExerciseService.softDeleteLocalExercise(event.id());
    }
}