package com.example.planupcore.domain.schedule.service;

import com.example.planupcore.domain.schedule.dto.ScheduleCreateDto;
import com.example.planupcore.domain.schedule.dto.ScheduleDetailDto;
import com.example.planupcore.domain.schedule.dto.ScheduleSummaryDto;
import com.example.planupcore.domain.schedule.dto.ScheduleUpdateDto;
import com.example.planupcore.domain.schedule.entity.Schedule;
import com.example.planupcore.domain.schedule.repository.ScheduleRepository;
import com.example.planupcore.global.exception.ErrorCode;
import com.example.planupcore.global.exception.ApiException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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
            request.endTime()
        );

        var newSchedule = scheduleRepository.save(schedule);
        return ScheduleDetailDto.fromEntity(newSchedule);
    }

    @Transactional(readOnly = true)
    public ScheduleDetailDto getScheduleById(UUID scheduleId) {
        var schedule = scheduleRepository.findById(scheduleId)
            .orElseThrow(() -> new ApiException(ErrorCode.SCHEDULE_NOT_FOUND));
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
                .orElseThrow(() -> new ApiException(ErrorCode.SCHEDULE_NOT_FOUND));

        var changed = false;

        if (request.title() != null) {
            schedule.changeTitle(request.title());
            changed = true;
        }

        if (request.description() != null) {
            schedule.changeDescription(request.description());
            changed = true;
        }

        if (request.startTime() != null || request.endTime() != null) {
            if (request.startTime() == null || request.endTime() == null) {
                throw new ApiException(ErrorCode.INVALID_SCHEDULE_TIME);
            }

            schedule.reschedule(request.startTime(), request.endTime());
            changed = true;
        }

        if (!changed) {
            throw new ApiException(ErrorCode.NO_UPDATE_FIELD);
        }

        return ScheduleDetailDto.fromEntity(schedule);
    }

    @Transactional
    public void deleteSchedule(UUID scheduleId) {
        var schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ApiException(ErrorCode.SCHEDULE_NOT_FOUND));
        scheduleRepository.delete(schedule);
    }
}
