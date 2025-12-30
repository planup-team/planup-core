package com.example.planupcore.domain.schedule.dto;

import com.example.planupcore.domain.schedule.entity.Schedule;
import com.example.planupcore.domain.schedule.entity.ScheduleType;

import java.time.LocalDateTime;
import java.util.UUID;

public record ScheduleDetailDto(
    UUID id,
    String title,
    String description,
    LocalDateTime startTime,
    LocalDateTime endTime,
    ScheduleType scheduleType,
    boolean movable,
    LocalDateTime createdAt
) {
    public static ScheduleDetailDto fromEntity(Schedule schedule) {
        return new ScheduleDetailDto(
            schedule.getId(),
            schedule.getTitle(),
            schedule.getDescription(),
            schedule.getStartTime(),
            schedule.getEndTime(),
            schedule.getScheduleType(),
            schedule.isMovable(),
            schedule.getCreatedAt()
        );
    }
}
