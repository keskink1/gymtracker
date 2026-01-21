package com.keskin.exercises.service.impl;

import com.keskin.exercises.config.RabbitMQConfig;
import com.keskin.exercises.config.UserContextHolder;
import com.keskin.exercises.dto.AdminExerciseDto;
import com.keskin.exercises.dto.message.ExerciseEventDto;
import com.keskin.exercises.dto.ExerciseDto;
import com.keskin.exercises.dto.request.CreateExerciseRequestDto;
import com.keskin.exercises.dto.request.UpdateExerciseRequestDto;
import com.keskin.exercises.entity.Exercise;
import com.keskin.exercises.exception.ResourceAlreadyExistsException;
import com.keskin.exercises.mapper.ExerciseMapper;
import com.keskin.exercises.repository.ExerciseRepository;
import com.keskin.exercises.service.IExerciseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ExerciseServiceImpl implements IExerciseService {

    private final ExerciseMapper exerciseMapper;
    private final ExerciseRepository exerciseRepository;
    private final RabbitTemplate rabbitTemplate;

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
        String email = UserContextHolder.getEmail();
        if (email == null || email.isBlank()) return List.of();

        return exerciseRepository.findAllByUserEmail(email.trim())
                .stream()
                .map(exerciseMapper::entityToDto) // Normal DTO
                .toList();
    }

    public List<AdminExerciseDto> getAllForAdmin() {
        if (!UserContextHolder.isAdmin()) {
            throw new RuntimeException("Unauthorized!");
        }
        return exerciseRepository.findAll()
                .stream()
                .map(exerciseMapper::entityToAdminDto)
                .toList();
    }

    @Override
    public ExerciseDto getExercise(Long id) {
        Exercise exercise = findAndValidate(id);
        return exerciseMapper.entityToDto(exercise);
    }

    @Override
    public AdminExerciseDto getExerciseForAdmin(Long id) {
        Exercise exercise = findAndValidate(id);
        return exerciseMapper.entityToAdminDto(exercise);
    }

    @Override
    public ExerciseDto createExercise(CreateExerciseRequestDto requestDto) {
        exerciseRepository.findByName(requestDto.name()).ifPresent(e -> {
            throw new ResourceAlreadyExistsException("Name already exists: " + requestDto.name());
        });

        Exercise newExercise = exerciseMapper.createRequestToEntity(requestDto);
        newExercise.setUserEmail(UserContextHolder.getEmail());

        exerciseRepository.save(newExercise);

        ExerciseEventDto createEvent = new ExerciseEventDto(
                newExercise.getId(),
                newExercise.getName(),
                newExercise.getMuscleGroup()
        );

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_KEY,
                createEvent
        );

        return exerciseMapper.entityToDto(newExercise);
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

        Exercise savedExercise = exerciseRepository.save(exercise);

        ExerciseEventDto updateEvent = new ExerciseEventDto(
                exercise.getId(),
                exercise.getName(),
                exercise.getMuscleGroup()
        );

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_KEY,
                updateEvent
        );

        log.info("Update message sent: {}", exercise.getName());
        return exerciseMapper.entityToDto(savedExercise);
    }

    @Override
    public void deleteExercise(Long id) {
        Exercise exercise = findAndValidate(id);
        exerciseRepository.delete(exercise);
    }
}