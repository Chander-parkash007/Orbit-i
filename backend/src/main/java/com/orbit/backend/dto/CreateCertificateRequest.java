package com.orbit.backend.dto;

import com.orbit.backend.enums.CertificateStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateCertificateRequest(
                @NotBlank
                String name,
                @NotBlank
                String role,
                @NotBlank
                String department,
                @NotNull
                LocalDate startDate,
                @NotNull
                LocalDate endDate,
                @NotBlank
                String duration,
                @NotBlank
                String certificateCode


) {
}
