package com.orbit.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateProjectRequest(
        @NotBlank
        String title,
        @NotBlank
        String description,
        @NotBlank
        String deadline,
        @NotNull
        Long clientId
) {
}
