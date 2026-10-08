package com.orbit.backend.dto;

import com.orbit.backend.enums.CertificateStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record CertificateVerifyResponse(
        String name,
        String role,
        String department,
        LocalDate startDate,
        LocalDate endDate,
        String duration,
        String status ,
        String certificateCode,
        String issuedBy

) {
}
