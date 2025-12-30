package com.example.planupcore.domain.schedule.controller;

import com.example.planupcore.domain.schedule.dto.ScheduleUpdateDto;
import com.example.planupcore.domain.schedule.service.ScheduleService;
import com.example.planupcore.domain.schedule.dto.ScheduleCreateDto;
import com.example.planupcore.domain.schedule.dto.ScheduleDetailDto;
import com.example.planupcore.domain.schedule.dto.ScheduleSummaryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/schedules")
@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleDetailDto> createSchedule(
        @RequestBody ScheduleCreateDto dto
    ) {
        // @TODO: Replace with authenticated user ID
        var schedule = scheduleService.saveSchedule(UUID.randomUUID(), dto);
        return ResponseEntity.status(201).body(schedule);
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleDetailDto> getScheduleDetail(
        @PathVariable UUID scheduleId
    ) {
        var schedule = scheduleService.getScheduleDetail(scheduleId);
        return ResponseEntity.ok(schedule);
    }

    @GetMapping("/{scheduleId}/summary")
    public ResponseEntity<ScheduleSummaryDto> getScheduleSummary(
        @PathVariable UUID scheduleId
    ) {
        var summary = scheduleService.getScheduleSummary(scheduleId);
        return ResponseEntity.ok(summary);
    }

    @PutMapping("/{scheduleId}")
    public ResponseEntity<ScheduleDetailDto> updateSchedule(
        @PathVariable UUID scheduleId,
        @RequestBody ScheduleUpdateDto dto
    ) {
        var updated = scheduleService.updateSchedule(scheduleId, dto);
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
