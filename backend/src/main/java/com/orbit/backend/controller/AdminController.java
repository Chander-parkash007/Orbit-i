package com.orbit.backend.controller;

import com.orbit.backend.dto.ClientUserResponse;
import com.orbit.backend.dto.ProjectResponse;
import com.orbit.backend.entities.ClientUser;
import com.orbit.backend.repository.ClientUserRepository;
import com.orbit.backend.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ClientUserRepository clientUserRepository;
    private final ProjectService projectService;

    @GetMapping("/clients")
    public ResponseEntity<List<ClientUserResponse>> getAllClients() {
        List<ClientUser> clients = clientUserRepository.findAll();
        List<ClientUserResponse> response = clients.stream()
                .map(c -> new ClientUserResponse(
                        c.getId(), c.getName(), c.getEmail(),
                        c.getCompany(), c.getPhone(),
                        c.isApproved(), c.getRole().name(), c.getCreatedAt()
                ))
                .toList();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/clients/{id}/approve")
    public ResponseEntity<String> approveClient(@PathVariable Long id) {
        ClientUser client = clientUserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        client.setApproved(true);
        clientUserRepository.save(client);
        return ResponseEntity.ok("Client approved successfully");
    }

    @GetMapping("/projects")
    public ResponseEntity<List<ProjectResponse>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }
}
