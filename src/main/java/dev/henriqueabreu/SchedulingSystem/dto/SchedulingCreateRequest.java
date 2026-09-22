package dev.henriqueabreu.SchedulingSystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record SchedulingCreateRequest(
        @NotBlank String title,
        @Size(max = 4000) String description,
        @NotNull LocalDateTime startDate,
        @NotNull LocalDateTime endDate,
        @NotBlank @Size(max = 80) String customer
) {
}
