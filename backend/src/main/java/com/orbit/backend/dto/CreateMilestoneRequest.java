package com.orbit.backend.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateMilestoneRequest(
                @NotBlank
                String title,
                @NotBlank
                String description,
                Integer sequenceNumber,
                LocalDate dueDate,
                @NotNull
                Long projectId
) {
}
