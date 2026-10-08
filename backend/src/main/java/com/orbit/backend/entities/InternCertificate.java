package com.orbit.backend.entities;

import com.orbit.backend.enums.CertificateStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "intern_certificates")
@Getter
@Setter
public class InternCertificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String role;
    @NotBlank
    private String department;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    @NotBlank
    private String duration;
    @Enumerated(EnumType.STRING)
    private CertificateStatus status = CertificateStatus.COMPLETED;
    @NotBlank
    @Column(unique = true)
    private String certificateCode;

    private String issuedBy ="ORBIT-I Private Limited";
    private LocalDate issuedAt;
    @CreationTimestamp
    private LocalDateTime createdAt;
}
