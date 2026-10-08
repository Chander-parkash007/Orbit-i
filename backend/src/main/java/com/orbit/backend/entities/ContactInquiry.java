package com.orbit.backend.entities;

import com.orbit.backend.enums.InquiryStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "contact_inquiries")
@Getter
@Setter
public class ContactInquiry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    @Email
    private String email;
    private String company;
    private String service;
    @NotNull
    @Column(columnDefinition = "TEXT")
    private String message;
    @Enumerated(EnumType.STRING)
    private InquiryStatus status = InquiryStatus.NEW;
    @CreationTimestamp
    private LocalDateTime createdAt;
}
