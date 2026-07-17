package com.projectanalyzer.dto.issue;

import com.projectanalyzer.entity.IssueCategory;
import com.projectanalyzer.entity.IssueStatus;
import com.projectanalyzer.entity.Severity;

import java.time.LocalDateTime;

public class IssueResponse {

    private Long id;
    private Long analysisId;
    private Severity severity;
    private IssueCategory category;
    private String title;
    private String description;
    private String filePath;
    private Integer lineNumber;
    private IssueStatus status;
    private String recommendation;
    private LocalDateTime createdAt;

    public IssueResponse() {}

    public IssueResponse(Long id, Long analysisId, Severity severity, IssueCategory category, String title, String description, String filePath, Integer lineNumber, IssueStatus status, String recommendation, LocalDateTime createdAt) {
        this.id = id;
        this.analysisId = analysisId;
        this.severity = severity;
        this.category = category;
        this.title = title;
        this.description = description;
        this.filePath = filePath;
        this.lineNumber = lineNumber;
        this.status = status;
        this.recommendation = recommendation;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getAnalysisId() { return analysisId; }
    public void setAnalysisId(Long analysisId) { this.analysisId = analysisId; }

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
        private Long analysisId;
        private Severity severity;
        private IssueCategory category;
        private String title;
        private String description;
        private String filePath;
        private Integer lineNumber;
        private IssueStatus status;
        private String recommendation;
        private LocalDateTime createdAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder analysisId(Long analysisId) { this.analysisId = analysisId; return this; }
        public Builder severity(Severity severity) { this.severity = severity; return this; }
        public Builder category(IssueCategory category) { this.category = category; return this; }
        public Builder title(String title) { this.title = title; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder filePath(String filePath) { this.filePath = filePath; return this; }
        public Builder lineNumber(Integer lineNumber) { this.lineNumber = lineNumber; return this; }
        public Builder status(IssueStatus status) { this.status = status; return this; }
        public Builder recommendation(String recommendation) { this.recommendation = recommendation; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public IssueResponse build() {
            return new IssueResponse(id, analysisId, severity, category, title, description, filePath, lineNumber, status, recommendation, createdAt);
        }
    }
}
