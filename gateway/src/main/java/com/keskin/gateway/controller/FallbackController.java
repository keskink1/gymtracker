package com.keskin.gateway.controller;

import com.keskin.gateway.dto.ErrorResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;


@RestController
@RequestMapping("/fallback")
@Tag(name = "Fallback page", description = "If one of the following services are down, it'll send a message to the user to inform required service is down.")
public class FallbackController {

    @GetMapping("/exercise")
    public ResponseEntity<ErrorResponseDto> exerciseFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(new ErrorResponseDto(
                        "Exercise Service",
                        "Exercise service is currently unavailable.",
                        "SERVICE_DOWN",
                        LocalDateTime.now().toString()
                ));
    }

    @GetMapping("/workout")
    public ResponseEntity<ErrorResponseDto> workoutFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(new ErrorResponseDto(
                        "Workout Service",
                        "Workout service is currently unavailable.",
                        "SERVICE_DOWN",
                        LocalDateTime.now().toString()
                ));    }

    @GetMapping("/user")
    public ResponseEntity<ErrorResponseDto> userServiceFallback() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(new ErrorResponseDto(
                        "User Service",
                        "User data is currently unavailable.",
                        "SERVICE_DOWN",
                        LocalDateTime.now().toString()
                ));
    }
}
