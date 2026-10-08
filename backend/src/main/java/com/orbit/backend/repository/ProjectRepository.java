package com.orbit.backend.repository;

import com.orbit.backend.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findAllByClientId(Long clientId);

    // Loads all projects + their milestones in ONE query — no N+1
    @Query("SELECT DISTINCT p FROM Project p LEFT JOIN FETCH p.milestones ORDER BY p.id")
    List<Project> findAllWithMilestones();

    // Loads all projects for a client + their milestones in ONE query
    @Query("SELECT DISTINCT p FROM Project p LEFT JOIN FETCH p.milestones WHERE p.client.id = :clientId ORDER BY p.id")
    List<Project> findAllByClientIdWithMilestones(@Param("clientId") Long clientId);
}
