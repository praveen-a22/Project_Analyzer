package com.projectanalyzer.repository;

import com.projectanalyzer.entity.TestingMetric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TestingRepository extends JpaRepository<TestingMetric, Long> {
    Optional<TestingMetric> findByAnalysisId(Long analysisId);
    Optional<TestingMetric> findTopByAnalysisProjectIdOrderByAnalysisAnalysisDateDesc(Long projectId);
}
