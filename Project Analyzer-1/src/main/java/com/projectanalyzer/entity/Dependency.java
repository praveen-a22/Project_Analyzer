package com.projectanalyzer.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "dependencies", indexes = {
    @Index(name = "idx_dep_analysis", columnList = "analysis_id")
})
public class Dependency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "analysis_id", nullable = false)
    private Analysis analysis;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(length = 50)
    private String version;

    @Column(length = 50)
    private String latestVersion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private DependencyStatus status = DependencyStatus.UP_TO_DATE;

    @Column(length = 20)
    private String severity = "None";

    public Dependency() {}

    public Dependency(Long id, Analysis analysis, String name, String version, String latestVersion, DependencyStatus status, String severity) {
        this.id = id;
        this.analysis = analysis;
        this.name = name;
        this.version = version;
        this.latestVersion = latestVersion;
        this.status = status != null ? status : DependencyStatus.UP_TO_DATE;
        this.severity = severity != null ? severity : "None";
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Analysis getAnalysis() { return analysis; }
    public void setAnalysis(Analysis analysis) { this.analysis = analysis; }

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
        private Analysis analysis;
        private String name;
        private String version;
        private String latestVersion;
        private DependencyStatus status = DependencyStatus.UP_TO_DATE;
        private String severity = "None";

        public Builder id(Long id) { this.id = id; return this; }
        public Builder analysis(Analysis analysis) { this.analysis = analysis; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder version(String version) { this.version = version; return this; }
        public Builder latestVersion(String latestVersion) { this.latestVersion = latestVersion; return this; }
        public Builder status(DependencyStatus status) { this.status = status; return this; }
        public Builder severity(String severity) { this.severity = severity; return this; }

        public Dependency build() {
            return new Dependency(id, analysis, name, version, latestVersion, status, severity);
        }
    }
}
