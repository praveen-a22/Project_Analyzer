package com.projectanalyzer.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "issues", indexes = {
    @Index(name = "idx_issue_analysis", columnList = "analysis_id"),
    @Index(name = "idx_issue_severity", columnList = "severity"),
    @Index(name = "idx_issue_category", columnList = "category")
})
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "analysis_id", nullable = false)
    private Analysis analysis;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Severity severity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private IssueCategory category;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 255)
    private String filePath;

    private Integer lineNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private IssueStatus status = IssueStatus.OPEN;

    @Column(columnDefinition = "TEXT")
    private String recommendation;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public Issue() {}

    public Issue(Long id, Analysis analysis, Severity severity, IssueCategory category, String title, String description, String filePath, Integer lineNumber, IssueStatus status, String recommendation, LocalDateTime createdAt) {
        this.id = id;
        this.analysis = analysis;
        this.severity = severity;
        this.category = category;
        this.title = title;
        this.description = description;
        this.filePath = filePath;
        this.lineNumber = lineNumber;
        this.status = status != null ? status : IssueStatus.OPEN;
        this.recommendation = recommendation;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Analysis getAnalysis() { return analysis; }
    public void setAnalysis(Analysis analysis) { this.analysis = analysis; }

    public Severity getSeverity() { return severity; }
    public void setSeverity(Severity severity) { this.severity = severity; }

    public IssueCategory getCategory() { return category; }
    public void setCategory(IssueCategory category) { this.category = category; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }

    public Integer getLineNumber() { return lineNumber; }
    public void setLineNumber(Integer lineNumber) { this.lineNumber = lineNumber; }

    public IssueStatus getStatus() { return status; }
    public void setStatus(IssueStatus status) { this.status = status; }

    public String getRecommendation() { return recommendation; }
    public void setRecommendation(String recommendation) { this.recommendation = recommendation; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private Analysis analysis;
        private Severity severity;
        private IssueCategory category;
        private String title;
        private String description;
        private String filePath;
        private Integer lineNumber;
        private IssueStatus status = IssueStatus.OPEN;
        private String recommendation;
        private LocalDateTime createdAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder analysis(Analysis analysis) { this.analysis = analysis; return this; }
        public Builder severity(Severity severity) { this.severity = severity; return this; }
        public Builder category(IssueCategory category) { this.category = category; return this; }
        public Builder title(String title) { this.title = title; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder filePath(String filePath) { this.filePath = filePath; return this; }
        public Builder lineNumber(Integer lineNumber) { this.lineNumber = lineNumber; return this; }
        public Builder status(IssueStatus status) { this.status = status; return this; }
        public Builder recommendation(String recommendation) { this.recommendation = recommendation; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public Issue build() {
            return new Issue(id, analysis, severity, category, title, description, filePath, lineNumber, status, recommendation, createdAt);
        }
    }
}
