package com.example.planupcore.domain.schedule.controller;

import com.example.planupcore.domain.schedule.dto.ScheduleUpdateDto;
import com.example.planupcore.domain.schedule.service.ScheduleService;
import com.example.planupcore.domain.schedule.dto.ScheduleCreateDto;
import com.example.planupcore.domain.schedule.dto.ScheduleDetailDto;
import com.example.planupcore.domain.schedule.dto.ScheduleSummaryDto;
import com.example.planupcore.global.advice.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/schedules")
@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleDetailDto> createSchedule(
        @RequestBody @Valid ScheduleCreateDto request
    ) {
        var schedule = scheduleService.createSchedule(UUID.randomUUID(), request);
        return ResponseEntity.status(201).body(schedule);
    }

    @GetMapping
    public ResponseEntity<List<ScheduleSummaryDto>> getAllSchedules() {
        var summary = scheduleService.getAllSchedules();
        return ResponseEntity.ok(summary);
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleDetailDto> getScheduleById(
        @PathVariable UUID scheduleId
    ) {
        var schedule = scheduleService.getScheduleById(scheduleId);
        return ResponseEntity.ok(schedule);
    }

    @PutMapping("/{scheduleId}")
    public ResponseEntity<ScheduleDetailDto> updateSchedule(
        @PathVariable UUID scheduleId,
        @RequestBody @Valid ScheduleUpdateDto request
    ) {
        var updated = scheduleService.updateSchedule(scheduleId, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(
        @PathVariable UUID scheduleId
    ) {
        scheduleService.deleteSchedule(scheduleId);
        return ResponseEntity.noContent().build();
    }
}
