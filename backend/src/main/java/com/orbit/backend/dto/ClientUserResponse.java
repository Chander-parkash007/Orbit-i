package com.orbit.backend.dto;

import java.time.LocalDateTime;

public record ClientUserResponse(
        Long id,
        String name,
        String email,
        String phone,
        String role,
        boolean company,
        String approved,
        LocalDateTime createdAt
) {
}
