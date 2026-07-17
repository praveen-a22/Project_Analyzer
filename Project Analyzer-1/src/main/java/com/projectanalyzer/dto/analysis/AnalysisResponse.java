package com.projectanalyzer.dto.analysis;

import java.time.LocalDateTime;

public class AnalysisResponse {

    private Long id;
    private Long projectId;
    private LocalDateTime analysisDate;
    private Double overallScore;
    private Double codeQualityScore;
    private Double securityScore;
    private Double architectureScore;
    private Double testingScore;
    private Double dependencyScore;
    private Double maintainabilityScore;
    private Integer totalFiles;
    private Integer totalLines;
    private Integer languageCount;
    private String status;

    public AnalysisResponse() {}

    public AnalysisResponse(Long id, Long projectId, LocalDateTime analysisDate, Double overallScore, Double codeQualityScore, Double securityScore, Double architectureScore, Double testingScore, Double dependencyScore, Double maintainabilityScore, Integer totalFiles, Integer totalLines, Integer languageCount, String status) {
        this.id = id;
        this.projectId = projectId;
        this.analysisDate = analysisDate;
        this.overallScore = overallScore;
        this.codeQualityScore = codeQualityScore;
        this.securityScore = securityScore;
        this.architectureScore = architectureScore;
        this.testingScore = testingScore;
        this.dependencyScore = dependencyScore;
        this.maintainabilityScore = maintainabilityScore;
        this.totalFiles = totalFiles;
        this.totalLines = totalLines;
        this.languageCount = languageCount;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getProjectId() { return projectId; }
    public void setProjectId(Long projectId) { this.projectId = projectId; }

    public LocalDateTime getAnalysisDate() { return analysisDate; }
    public void setAnalysisDate(LocalDateTime analysisDate) { this.analysisDate = analysisDate; }

    public Double getOverallScore() { return overallScore; }
    public void setOverallScore(Double overallScore) { this.overallScore = overallScore; }

    public Double getCodeQualityScore() { return codeQualityScore; }
    public void setCodeQualityScore(Double codeQualityScore) { this.codeQualityScore = codeQualityScore; }

    public Double getSecurityScore() { return securityScore; }
    public void setSecurityScore(Double securityScore) { this.securityScore = securityScore; }

    public Double getArchitectureScore() { return architectureScore; }
    public void setArchitectureScore(Double architectureScore) { this.architectureScore = architectureScore; }

    public Double getTestingScore() { return testingScore; }
    public void setTestingScore(Double testingScore) { this.testingScore = testingScore; }

    public Double getDependencyScore() { return dependencyScore; }
    public void setDependencyScore(Double dependencyScore) { this.dependencyScore = dependencyScore; }

    public Double getMaintainabilityScore() { return maintainabilityScore; }
    public void setMaintainabilityScore(Double maintainabilityScore) { this.maintainabilityScore = maintainabilityScore; }

    public Integer getTotalFiles() { return totalFiles; }
    public void setTotalFiles(Integer totalFiles) { this.totalFiles = totalFiles; }

    public Integer getTotalLines() { return totalLines; }
    public void setTotalLines(Integer totalLines) { this.totalLines = totalLines; }

    public Integer getLanguageCount() { return languageCount; }
    public void setLanguageCount(Integer languageCount) { this.languageCount = languageCount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private Long projectId;
        private LocalDateTime analysisDate;
        private Double overallScore;
        private Double codeQualityScore;
        private Double securityScore;
        private Double architectureScore;
        private Double testingScore;
        private Double dependencyScore;
        private Double maintainabilityScore;
        private Integer totalFiles;
        private Integer totalLines;
        private Integer languageCount;
        private String status;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder projectId(Long projectId) { this.projectId = projectId; return this; }
        public Builder analysisDate(LocalDateTime analysisDate) { this.analysisDate = analysisDate; return this; }
        public Builder overallScore(Double overallScore) { this.overallScore = overallScore; return this; }
        public Builder codeQualityScore(Double codeQualityScore) { this.codeQualityScore = codeQualityScore; return this; }
        public Builder securityScore(Double securityScore) { this.securityScore = securityScore; return this; }
        public Builder architectureScore(Double architectureScore) { this.architectureScore = architectureScore; return this; }
        public Builder testingScore(Double testingScore) { this.testingScore = testingScore; return this; }
        public Builder dependencyScore(Double dependencyScore) { this.dependencyScore = dependencyScore; return this; }
        public Builder maintainabilityScore(Double maintainabilityScore) { this.maintainabilityScore = maintainabilityScore; return this; }
        public Builder totalFiles(Integer totalFiles) { this.totalFiles = totalFiles; return this; }
        public Builder totalLines(Integer totalLines) { this.totalLines = totalLines; return this; }
        public Builder languageCount(Integer languageCount) { this.languageCount = languageCount; return this; }
        public Builder status(String status) { this.status = status; return this; }

        public AnalysisResponse build() {
            return new AnalysisResponse(id, projectId, analysisDate, overallScore, codeQualityScore, securityScore, architectureScore, testingScore, dependencyScore, maintainabilityScore, totalFiles, totalLines, languageCount, status);
        }
    }
}
