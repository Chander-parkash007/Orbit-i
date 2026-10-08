package com.orbit.backend.dto;


import java.time.LocalDate;

public record MilestoneResponse(
        Long id,
        String title,
        String description,
        Integer sequenceNumber,
        LocalDate dueDate,
        LocalDate completionDate,
        String status
) {
}
