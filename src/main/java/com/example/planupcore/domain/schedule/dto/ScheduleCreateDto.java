package com.example.planupcore.domain.schedule.dto;

import com.example.planupcore.domain.schedule.entity.ScheduleType;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record ScheduleCreateDto(
    @NotBlank(message = "Title is required")
    String title,

    @NotBlank(message = "Description is required")
    String description,

    LocalDateTime startTime,

    LocalDateTime endTime
) {}
