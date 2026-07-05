package com.projectanalyzer.repository;

import com.projectanalyzer.entity.ArchitectureMetric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArchitectureRepository extends JpaRepository<ArchitectureMetric, Long> {
    Optional<ArchitectureMetric> findByAnalysisId(Long analysisId);
    Optional<ArchitectureMetric> findTopByAnalysisProjectIdOrderByAnalysisAnalysisDateDesc(Long projectId);
}
