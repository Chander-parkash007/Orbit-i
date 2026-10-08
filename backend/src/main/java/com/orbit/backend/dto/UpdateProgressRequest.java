package com.orbit.backend.dto;

import com.orbit.backend.enums.ProjectStatus;

public record UpdateProgressRequest(
        String progress,
        ProjectStatus status
) {
}
