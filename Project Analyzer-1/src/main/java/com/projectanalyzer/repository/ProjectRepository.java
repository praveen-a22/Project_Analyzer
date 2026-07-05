package com.projectanalyzer.repository;

import com.projectanalyzer.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByOwnerIdOrderByCreatedAtDesc(Long ownerId);
    Page<Project> findByOwnerId(Long ownerId, Pageable pageable);
    Optional<Project> findByIdAndOwnerId(Long id, Long ownerId);
}
