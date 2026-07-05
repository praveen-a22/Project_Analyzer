package com.projectanalyzer.repository;

import com.projectanalyzer.entity.Analysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnalysisRepository extends JpaRepository<Analysis, Long> {
    List<Analysis> findByProjectIdOrderByAnalysisDateDesc(Long projectId);
    Optional<Analysis> findTopByProjectIdOrderByAnalysisDateDesc(Long projectId);
}
