package com.projectanalyzer.repository;

import com.projectanalyzer.entity.Dependency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DependencyRepository extends JpaRepository<Dependency, Long> {
    List<Dependency> findByAnalysisProjectId(Long projectId);
}
