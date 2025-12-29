package com.keskin.gateway.dto;

public record ErrorResponseDto(
        String service,
        String message,
        String status,
        String timestamp
) {}