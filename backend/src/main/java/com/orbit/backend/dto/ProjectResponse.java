package com.orbit.backend.dto;

import com.orbit.backend.enums.ProjectStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record ProjectResponse(
        Long id,
        String title,
        String description,
        ProjectStatus status,
        LocalDate startDate,
        LocalDate deadline,
        LocalDate completedAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Integer progress,
        Long clientId,
        List<MilestoneResponse> milestones
) {
}
