package com.projectanalyzer.dto.dependency;

import com.projectanalyzer.entity.DependencyStatus;

public class DependencyResponse {
    private Long id;
    private Long analysisId;
    private String name;
    private String version;
    private String latestVersion;
    private DependencyStatus status;
    private String severity;

    public DependencyResponse() {}

    public DependencyResponse(Long id, Long analysisId, String name, String version, String latestVersion, DependencyStatus status, String severity) {
        this.id = id;
        this.analysisId = analysisId;
        this.name = name;
        this.version = version;
        this.latestVersion = latestVersion;
        this.status = status;
        this.severity = severity;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getAnalysisId() { return analysisId; }
    public void setAnalysisId(Long analysisId) { this.analysisId = analysisId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }

    public String getLatestVersion() { return latestVersion; }
    public void setLatestVersion(String latestVersion) { this.latestVersion = latestVersion; }

    public DependencyStatus getStatus() { return status; }
    public void setStatus(DependencyStatus status) { this.status = status; }

    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private Long analysisId;
        private String name;
        private String version;
        private String latestVersion;
        private DependencyStatus status;
        private String severity;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder analysisId(Long analysisId) { this.analysisId = analysisId; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder version(String version) { this.version = version; return this; }
        public Builder latestVersion(String latestVersion) { this.latestVersion = latestVersion; return this; }
        public Builder status(DependencyStatus status) { this.status = status; return this; }
        public Builder severity(String severity) { this.severity = severity; return this; }

        public DependencyResponse build() {
            return new DependencyResponse(id, analysisId, name, version, latestVersion, status, severity);
        }
    }
}
