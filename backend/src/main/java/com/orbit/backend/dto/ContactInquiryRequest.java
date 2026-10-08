package com.orbit.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ContactInquiryRequest(
        @NotBlank
        String name,
        @NotBlank
        @Email
        String email,
        String company,
        String service,
        @NotBlank
        String message
) { }
