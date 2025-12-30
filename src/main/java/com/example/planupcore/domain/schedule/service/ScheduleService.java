package com.example.planupcore.domain.schedule.service;

import com.example.planupcore.domain.schedule.dto.ScheduleCreateDto;
import com.example.planupcore.domain.schedule.dto.ScheduleDetailDto;
import com.example.planupcore.domain.schedule.dto.ScheduleSummaryDto;
import com.example.planupcore.domain.schedule.dto.ScheduleUpdateDto;
import com.example.planupcore.domain.schedule.entity.Schedule;
import com.example.planupcore.domain.schedule.repository.ScheduleRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

/*

    @TODO: Better exception handling

*/

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Transactional
    public ScheduleDetailDto saveSchedule(UUID userId, ScheduleCreateDto dto) {
        var schedule = Schedule.create(
            userId,
            dto.title(),
            dto.description(),
            dto.startTime(),
            dto.endTime(),
            dto.scheduleType(),
            dto.movable()
        );

        var newSchedule = scheduleRepository.save(schedule);
        return ScheduleDetailDto.fromEntity(newSchedule);
    }

    @Transactional(readOnly = true)
    public ScheduleDetailDto getScheduleDetail(UUID scheduleId) {
        var schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found with id: " + scheduleId));
        return ScheduleDetailDto.fromEntity(schedule);
    }

    @Transactional(readOnly = true)
    public ScheduleSummaryDto getScheduleSummary(UUID scheduleId) {
        var schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found with id: " + scheduleId));
        return ScheduleSummaryDto.fromEntity(schedule);
    }

    @Transactional
    public ScheduleDetailDto updateSchedule(UUID scheduleId, ScheduleUpdateDto dto) {
        var schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found with id: " + scheduleId));

        schedule.changeTitle(dto.title());
        schedule.changeDescription(dto.description());
        schedule.changeScheduleType(dto.scheduleType());
        schedule.changeMovable(dto.movable());
        schedule.reschedule(dto.startTime(), dto.endTime());

        return ScheduleDetailDto.fromEntity(schedule);
    }

    @Transactional
    public void deleteSchedule(UUID scheduleId) {
        var schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found with id: " + scheduleId));
        scheduleRepository.delete(schedule);
    }
}
