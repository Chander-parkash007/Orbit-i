package com.orbit.backend.entities;

import com.orbit.backend.enums.ProjectStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "project_details")
@Getter
@Setter
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String projectTitle;
    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String projectDescription;
    @Enumerated(EnumType.STRING)
    private ProjectStatus status = ProjectStatus.PLANING;

    private LocalDate startDate;
    private LocalDate deadline;
    private LocalDate compeltedAt;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @NotNull(message = "Progress cannot be null")
    @Min(value = 0, message = "Progress must be at least 0")
    @Max(value = 100, message = "Progress cannot exceed 100")
    @Column(name = "progress_percentage", nullable = false)
    private Integer progressPercentage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private ClientUser client;

    // mappedBy tells JPA "the FK lives on the ProjectMilestone side"
    // LAZY so we don't load milestones unless we explicitly JOIN FETCH them
    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
    private List<ProjectMilestone> milestones = new ArrayList<>();
}
