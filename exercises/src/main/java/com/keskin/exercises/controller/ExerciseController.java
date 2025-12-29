package com.keskin.exercises.controller;

import com.keskin.exercises.dto.ExerciseDto;
import com.keskin.exercises.dto.request.CreateExerciseRequestDto;
import com.keskin.exercises.dto.request.UpdateExerciseRequestDto;
import com.keskin.exercises.service.IExerciseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/exercises")
@RequiredArgsConstructor
@Tag(name = "Exercises page", description = "Manage exercises in this page. Contains, reading, creating, updating and deleting.")
public class ExerciseController {

    private final IExerciseService exerciseService;

    @GetMapping("/all")
    public ResponseEntity<List<ExerciseDto>> getAll() {
        return ResponseEntity.ok(exerciseService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExerciseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(exerciseService.getExercise(id));
    }

    @PostMapping
    public ResponseEntity<ExerciseDto> createExercise(@Valid @RequestBody CreateExerciseRequestDto requestDto) {
        return new ResponseEntity<>(exerciseService.createExercise(requestDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExerciseDto> updateExercise(@PathVariable Long id, @Valid @RequestBody UpdateExerciseRequestDto requestDto) {
        return ResponseEntity.ok(exerciseService.updateExercise(requestDto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExercise(@PathVariable Long id) {
        exerciseService.deleteExercise(id);
        return ResponseEntity.noContent().build();
    }
}