package com.projectanalyzer.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "analyses", indexes = {
    @Index(name = "idx_analysis_project", columnList = "project_id")
})
public class Analysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @CreationTimestamp
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

    @Column(length = 20)
    private String status = "COMPLETED";

    @OneToMany(mappedBy = "analysis", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Issue> issues = new ArrayList<>();

    @OneToMany(mappedBy = "analysis", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Dependency> dependencies = new ArrayList<>();

    @OneToOne(mappedBy = "analysis", cascade = CascadeType.ALL, orphanRemoval = true)
    private ArchitectureMetric architectureMetric;

    @OneToOne(mappedBy = "analysis", cascade = CascadeType.ALL, orphanRemoval = true)
    private TestingMetric testingMetric;

    public Analysis() {}

    public Analysis(Long id, Project project, LocalDateTime analysisDate, Double overallScore, Double codeQualityScore, Double securityScore, Double architectureScore, Double testingScore, Double dependencyScore, Double maintainabilityScore, Integer totalFiles, Integer totalLines, Integer languageCount, String status, List<Issue> issues, List<Dependency> dependencies, ArchitectureMetric architectureMetric, TestingMetric testingMetric) {
        this.id = id;
        this.project = project;
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
        this.status = status != null ? status : "COMPLETED";
        this.issues = issues != null ? issues : new ArrayList<>();
        this.dependencies = dependencies != null ? dependencies : new ArrayList<>();
        this.architectureMetric = architectureMetric;
        this.testingMetric = testingMetric;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Project getProject() { return project; }
    public void setProject(Project project) { this.project = project; }

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

    public List<Issue> getIssues() { return issues; }
    public void setIssues(List<Issue> issues) { this.issues = issues; }

    public List<Dependency> getDependencies() { return dependencies; }
    public void setDependencies(List<Dependency> dependencies) { this.dependencies = dependencies; }

    public ArchitectureMetric getArchitectureMetric() { return architectureMetric; }
    public void setArchitectureMetric(ArchitectureMetric architectureMetric) { this.architectureMetric = architectureMetric; }

    public TestingMetric getTestingMetric() { return testingMetric; }
    public void setTestingMetric(TestingMetric testingMetric) { this.testingMetric = testingMetric; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private Project project;
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
        private String status = "COMPLETED";
        private List<Issue> issues = new ArrayList<>();
        private List<Dependency> dependencies = new ArrayList<>();
        private ArchitectureMetric architectureMetric;
        private TestingMetric testingMetric;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder project(Project project) { this.project = project; return this; }
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
        public Builder issues(List<Issue> issues) { this.issues = issues; return this; }
        public Builder dependencies(List<Dependency> dependencies) { this.dependencies = dependencies; return this; }
        public Builder architectureMetric(ArchitectureMetric architectureMetric) { this.architectureMetric = architectureMetric; return this; }
        public Builder testingMetric(TestingMetric testingMetric) { this.testingMetric = testingMetric; return this; }

        public Analysis build() {
            return new Analysis(id, project, analysisDate, overallScore, codeQualityScore, securityScore, architectureScore, testingScore, dependencyScore, maintainabilityScore, totalFiles, totalLines, languageCount, status, issues, dependencies, architectureMetric, testingMetric);
        }
    }
}
