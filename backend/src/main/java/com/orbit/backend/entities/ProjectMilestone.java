package com.orbit.backend.entities;

import com.orbit.backend.enums.MilestoneStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Table(name = "project_milestones")
@Getter
@Setter
public class ProjectMilestone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;
    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String description;
    private Integer sequenceNumber;
    private LocalDate dueDate;
    private LocalDate completionDate;
    @CreationTimestamp
    private LocalDate createdAt;
    @Enumerated(EnumType.STRING)
private MilestoneStatus status = MilestoneStatus.PENDING;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;
}
