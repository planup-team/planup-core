package com.example.planupcore.domain.schedule.dto;

import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record ScheduleUpdateDto(
    @Size(min = 5, max = 100, message = "Title must be between 5 and 100 characters")
    String title,

    @Size(min = 10, max = 500, message = "Description must be between 10 and 500 characters")
    String description,

    LocalDateTime startTime,

    LocalDateTime endTime
) {}
