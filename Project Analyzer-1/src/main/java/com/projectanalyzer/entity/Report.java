package com.projectanalyzer.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "reports", indexes = {
    @Index(name = "idx_report_project", columnList = "project_id")
})
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "analysis_id")
    private Analysis analysis;

    @Column(nullable = false, length = 100)
    private String reportType;

    @Column(length = 255)
    private String filePath;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public Report() {}

    public Report(Long id, Project project, Analysis analysis, String reportType, String filePath, LocalDateTime createdAt) {
        this.id = id;
        this.project = project;
        this.analysis = analysis;
        this.reportType = reportType;
        this.filePath = filePath;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Project getProject() { return project; }
    public void setProject(Project project) { this.project = project; }

    public Analysis getAnalysis() { return analysis; }
    public void setAnalysis(Analysis analysis) { this.analysis = analysis; }

    public String getReportType() { return reportType; }
    public void setReportType(String reportType) { this.reportType = reportType; }

    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private Project project;
        private Analysis analysis;
        private String reportType;
        private String filePath;
        private LocalDateTime createdAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder project(Project project) { this.project = project; return this; }
        public Builder analysis(Analysis analysis) { this.analysis = analysis; return this; }
        public Builder reportType(String reportType) { this.reportType = reportType; return this; }
        public Builder filePath(String filePath) { this.filePath = filePath; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public Report build() {
            return new Report(id, project, analysis, reportType, filePath, createdAt);
        }
    }
}
