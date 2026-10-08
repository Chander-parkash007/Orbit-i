package com.orbit.backend.service;

import com.orbit.backend.dto.CreateMilestoneRequest;
import com.orbit.backend.dto.CreateProjectRequest;
import com.orbit.backend.dto.MilestoneResponse;
import com.orbit.backend.dto.ProjectResponse;
import com.orbit.backend.dto.UpdateProgressRequest;
import com.orbit.backend.entities.ClientUser;
import com.orbit.backend.entities.Project;
import com.orbit.backend.entities.ProjectMilestone;
import com.orbit.backend.enums.MilestoneStatus;
import com.orbit.backend.repository.ClientUserRepository;
import com.orbit.backend.repository.ProjectMilestoneRepository;
import com.orbit.backend.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMilestoneRepository milestoneRepository;
    private final ClientUserRepository clientUserRepository;

    // Admin — create a project for a client
    public String createProject(CreateProjectRequest request) {
        ClientUser client = clientUserRepository.findById(request.clientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));

        Project project = new Project();
        project.setProjectTitle(request.title());
        project.setProjectDescription(request.description());
        project.setDeadline(LocalDate.parse(request.deadline()));
        project.setClient(client);
        project.setProgressPercentage(0);

        projectRepository.save(project);
        return "Project created successfully";
    }

    // Admin — add milestone to a project
    public String addMilestone(CreateMilestoneRequest request) {
        Project project = projectRepository.findById(request.projectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        ProjectMilestone milestone = new ProjectMilestone();
        milestone.setTitle(request.title());
        milestone.setDescription(request.description());
        milestone.setDueDate(request.dueDate());
        milestone.setSequenceNumber(request.sequenceNumber());
        milestone.setProject(project);
        milestone.setStatus(MilestoneStatus.PENDING);

        milestoneRepository.save(milestone);
        return "Milestone added successfully";
    }

    // Admin — update project progress and status
    public String updateProgress(Long projectId, UpdateProgressRequest request) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        project.setProgressPercentage(Integer.valueOf(request.progress()));
        project.setStatus(request.status());

        projectRepository.save(project);
        return "Progress updated successfully";
    }

    // ── shared mapper ──────────────────────────────────────────────────────────
    // milestones are already loaded by the JOIN FETCH — no extra query per project
    private ProjectResponse toResponse(Project project) {
        List<MilestoneResponse> milestones = project.getMilestones()
                .stream()
                .sorted(Comparator.comparingInt(m -> m.getSequenceNumber() != null ? m.getSequenceNumber() : 0))
                .map(m -> new MilestoneResponse(
                        m.getId(),
                        m.getTitle(),
                        m.getDescription(),
                        m.getSequenceNumber(),
                        m.getDueDate(),
                        m.getCompletionDate(),
                        m.getStatus().name()
                ))
                .toList();

        return new ProjectResponse(
                project.getId(),
                project.getProjectTitle(),
                project.getProjectDescription(),
                project.getStatus(),
                project.getStartDate(),
                project.getDeadline(),
                project.getCompeltedAt(),
                project.getCreatedAt(),
                project.getUpdatedAt(),
                project.getProgressPercentage(),
                project.getClient() != null ? project.getClient().getId() : null,
                milestones
        );
    }

    // Admin — get all projects (2 queries total: 1 for projects+milestones JOIN FETCH)
    public List<ProjectResponse> getAllProjects() {
        return projectRepository.findAllWithMilestones()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // Client — get all their projects with milestones (2 queries total)
    public List<ProjectResponse> getClientProjects(Long clientId) {
        return projectRepository.findAllByClientIdWithMilestones(clientId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // Single project by id — still fine, only 1 project so no N+1 possible
    public ProjectResponse getProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        // milestones not JOIN FETCHed here — trigger the lazy load explicitly
        project.getMilestones().size(); // init the collection
        return toResponse(project);
    }
}
