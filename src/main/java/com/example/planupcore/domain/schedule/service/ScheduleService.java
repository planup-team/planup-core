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

import java.util.List;
import java.util.UUID;

/*

    @TODO: Better exception handling

*/

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Transactional
    public ScheduleDetailDto createSchedule(UUID userId, ScheduleCreateDto request) {
        var schedule = Schedule.create(
            userId,
            request.title(),
            request.description(),
            request.startTime(),
            request.endTime(),
            request.scheduleType(),
            request.movable()
        );

        var newSchedule = scheduleRepository.save(schedule);
        return ScheduleDetailDto.fromEntity(newSchedule);
    }

    @Transactional(readOnly = true)
    public ScheduleDetailDto getScheduleById(UUID scheduleId) {
        var schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found with id: " + scheduleId));
        return ScheduleDetailDto.fromEntity(schedule);
    }

    @Transactional(readOnly = true)
    public List<ScheduleSummaryDto> getAllSchedules() {
        var schedule = scheduleRepository.findAll();
        return schedule.stream()
                .map(ScheduleSummaryDto::fromEntity)
                .toList();
    }

    @Transactional
    public ScheduleDetailDto updateSchedule(UUID scheduleId, ScheduleUpdateDto request) {
        var schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found with id: " + scheduleId));

        schedule.changeTitle(request.title());
        schedule.changeDescription(request.description());
        schedule.changeScheduleType(request.scheduleType());
        schedule.changeMovable(request.movable());
        schedule.reschedule(request.startTime(), request.endTime());

        return ScheduleDetailDto.fromEntity(schedule);
    }

    @Transactional
    public void deleteSchedule(UUID scheduleId) {
        var schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found with id: " + scheduleId));
        scheduleRepository.delete(schedule);
    }
}
