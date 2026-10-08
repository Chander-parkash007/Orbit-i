package com.orbit.backend.controller;

import com.orbit.backend.dto.CreateMilestoneRequest;
import com.orbit.backend.dto.CreateProjectRequest;
import com.orbit.backend.dto.ProjectResponse;
import com.orbit.backend.dto.UpdateProgressRequest;
import com.orbit.backend.entities.ClientUser;
import com.orbit.backend.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    // Client — get their own projects
    @GetMapping("/my")
    public ResponseEntity<List<ProjectResponse>> myProjects(
            @AuthenticationPrincipal ClientUser user) {
        return ResponseEntity.ok(projectService.getClientProjects(user.getId()));
    }

    // Client — get single project
    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponse> getProject(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getProject(id));
    }

    // Admin — create project
    @PostMapping("/admin/create")
    public ResponseEntity<String> createProject(
            @Valid @RequestBody CreateProjectRequest request) {
        return ResponseEntity.ok(projectService.createProject(request));
    }

    // Admin — add milestone
    @PostMapping("/admin/milestone")
    public ResponseEntity<String> addMilestone(
            @Valid @RequestBody CreateMilestoneRequest request) {
        return ResponseEntity.ok(projectService.addMilestone(request));
    }

    // Admin — update progress
    @PatchMapping("/admin/{id}/progress")
    public ResponseEntity<String> updateProgress(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProgressRequest request) {
        return ResponseEntity.ok(projectService.updateProgress(id, request));
    }
}
