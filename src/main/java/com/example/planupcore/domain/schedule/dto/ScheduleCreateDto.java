package com.example.planupcore.domain.schedule.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record ScheduleCreateDto(
    @NotBlank(message = "Title is required")
    String title,

    @NotBlank(message = "Description is required")
    String description,

    @NotBlank(message = "Start time is required")
    LocalDateTime startTime,

    @NotBlank(message = "End time is required")
    LocalDateTime endTime
) {}
