package com.example.planupcore.domain.schedule.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "schedules")
@Getter
public class Schedule {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(name = "user_id", nullable = false, columnDefinition = "uuid")
    private UUID userId;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    protected Schedule() {}

    private Schedule(
        UUID userId,
        String title,
        String description,
        LocalDateTime startTime,
        LocalDateTime endTime
    ) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
    };

    public static Schedule create(
        UUID userId,
        String title,
        String description,
        LocalDateTime startTime,
        LocalDateTime endTime
    ) {
        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("startTime > endTime");
        }

        return new Schedule(
            userId,
            title,
            description,
            startTime,
            endTime
        );
    }

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeDescription(String description) {
        this.description = description;
    }

    public void reschedule(LocalDateTime newStart, LocalDateTime newEnd) {
        if (startTime.equals(newStart) && endTime.equals(newEnd)) {
            return;
        }

        if (newStart.isAfter(newEnd)) {
            throw new IllegalArgumentException("startTime > endTime");
        }

        this.startTime = newStart;
        this.endTime = newEnd;
    }
}
