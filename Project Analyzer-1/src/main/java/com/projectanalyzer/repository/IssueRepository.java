package com.projectanalyzer.repository;

import com.projectanalyzer.entity.Issue;
import com.projectanalyzer.entity.IssueCategory;
import com.projectanalyzer.entity.IssueStatus;
import com.projectanalyzer.entity.Severity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {
    List<Issue> findByAnalysisProjectId(Long projectId);

    @Query("SELECT i FROM Issue i WHERE i.analysis.project.id = :projectId " +
           "AND (:severity IS NULL OR i.severity = :severity) " +
           "AND (:category IS NULL OR i.category = :category) " +
           "AND (:status IS NULL OR i.status = :status)")
    List<Issue> filterIssues(
            @Param("projectId") Long projectId,
            @Param("severity") Severity severity,
            @Param("category") IssueCategory category,
            @Param("status") IssueStatus status
    );

    Page<Issue> findByAnalysisProjectId(Long projectId, Pageable pageable);
}
