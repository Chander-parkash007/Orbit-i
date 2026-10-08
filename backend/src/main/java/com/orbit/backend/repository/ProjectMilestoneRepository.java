package com.orbit.backend.repository;

import com.orbit.backend.entities.ProjectMilestone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectMilestoneRepository extends JpaRepository<ProjectMilestone,Long> {

    List<ProjectMilestone> findAllByProjectIdOrderBySequenceNumber(Long id);
}
