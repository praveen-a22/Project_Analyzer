package com.projectanalyzer.dto.report;

import java.time.LocalDateTime;

public class ReportResponse {

    private Long id;
    private Long projectId;
    private Long analysisId;
    private String reportType;
    private String filePath;
    private LocalDateTime createdAt;

    public ReportResponse() {}

    public ReportResponse(Long id, Long projectId, Long analysisId, String reportType, String filePath, LocalDateTime createdAt) {
        this.id = id;
        this.projectId = projectId;
        this.analysisId = analysisId;
        this.reportType = reportType;
        this.filePath = filePath;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getProjectId() { return projectId; }
    public void setProjectId(Long projectId) { this.projectId = projectId; }

    public Long getAnalysisId() { return analysisId; }
    public void setAnalysisId(Long analysisId) { this.analysisId = analysisId; }

    public String getReportType() { return reportType; }
    public void setReportType(String reportType) { this.reportType = reportType; }

    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private Long projectId;
        private Long analysisId;
        private String reportType;
        private String filePath;
        private LocalDateTime createdAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder projectId(Long projectId) { this.projectId = projectId; return this; }
        public Builder analysisId(Long analysisId) { this.analysisId = analysisId; return this; }
        public Builder reportType(String reportType) { this.reportType = reportType; return this; }
        public Builder filePath(String filePath) { this.filePath = filePath; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public ReportResponse build() {
            return new ReportResponse(id, projectId, analysisId, reportType, filePath, createdAt);
        }
    }
}
