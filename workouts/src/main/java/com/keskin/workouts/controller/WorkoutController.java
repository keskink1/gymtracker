package com.keskin.workouts.controller;

import com.keskin.workouts.dto.WorkoutDto;
import com.keskin.workouts.dto.requests.CreateWorkoutRequestDto;
import com.keskin.workouts.dto.requests.UpdateWorkoutRequestDto;
import com.keskin.workouts.service.IWorkoutService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/workouts")
@RequiredArgsConstructor
public class WorkoutController {

    private final IWorkoutService workoutService;

    @GetMapping
    public ResponseEntity<List<WorkoutDto>> getAll() {
        return ResponseEntity.ok(workoutService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkoutDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(workoutService.getWorkout(id));
    }

    @PostMapping
    public ResponseEntity<WorkoutDto> create(@Valid @RequestBody CreateWorkoutRequestDto requestDto) {
        WorkoutDto savedWorkout = workoutService.createWorkout(requestDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedWorkout.id())
                .toUri();

        return ResponseEntity.created(location).body(savedWorkout);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkoutDto> update(@PathVariable Long id,
                                             @Valid @RequestBody UpdateWorkoutRequestDto requestDto) {
        return ResponseEntity.ok(workoutService.updateWorkout(id, requestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        workoutService.deleteWorkout(id);
        return ResponseEntity.noContent().build();
    }
}