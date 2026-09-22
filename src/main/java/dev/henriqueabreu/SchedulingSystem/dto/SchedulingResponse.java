package dev.henriqueabreu.SchedulingSystem.dto;

import dev.henriqueabreu.SchedulingSystem.model.StatusScheduling;

import java.time.LocalDateTime;

public record SchedulingResponse(
        Long id,
        String title,
        String description,
        LocalDateTime startDate,
        LocalDateTime endDate,
        StatusScheduling status,
        String customer,
        LocalDateTime createdIn,
        LocalDateTime updatedIn
) {
}
