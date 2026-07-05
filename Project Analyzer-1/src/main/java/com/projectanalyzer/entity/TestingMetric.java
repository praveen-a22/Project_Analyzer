package com.projectanalyzer.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "testing_metrics")
public class TestingMetric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "analysis_id", nullable = false)
    private Analysis analysis;

    private Integer testCount = 0;
    private Integer testClassCount = 0;
    private Integer passedCount = 0;
    private Integer failedCount = 0;
    private Double coveragePercent = 0.0;
    private Boolean coverageAvailable = false;

    public TestingMetric() {}

    public TestingMetric(Long id, Analysis analysis, Integer testCount, Integer testClassCount, Integer passedCount, Integer failedCount, Double coveragePercent, Boolean coverageAvailable) {
        this.id = id;
        this.analysis = analysis;
        this.testCount = testCount != null ? testCount : 0;
        this.testClassCount = testClassCount != null ? testClassCount : 0;
        this.passedCount = passedCount != null ? passedCount : 0;
        this.failedCount = failedCount != null ? failedCount : 0;
        this.coveragePercent = coveragePercent != null ? coveragePercent : 0.0;
        this.coverageAvailable = coverageAvailable != null ? coverageAvailable : false;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Analysis getAnalysis() { return analysis; }
    public void setAnalysis(Analysis analysis) { this.analysis = analysis; }

    public Integer getTestCount() { return testCount; }
    public void setTestCount(Integer testCount) { this.testCount = testCount; }

    public Integer getTestClassCount() { return testClassCount; }
    public void setTestClassCount(Integer testClassCount) { this.testClassCount = testClassCount; }

    public Integer getPassedCount() { return passedCount; }
    public void setPassedCount(Integer passedCount) { this.passedCount = passedCount; }

    public Integer getFailedCount() { return failedCount; }
    public void setFailedCount(Integer failedCount) { this.failedCount = failedCount; }

    public Double getCoveragePercent() { return coveragePercent; }
    public void setCoveragePercent(Double coveragePercent) { this.coveragePercent = coveragePercent; }

    public Boolean getCoverageAvailable() { return coverageAvailable; }
    public void setCoverageAvailable(Boolean coverageAvailable) { this.coverageAvailable = coverageAvailable; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private Analysis analysis;
        private Integer testCount = 0;
        private Integer testClassCount = 0;
        private Integer passedCount = 0;
        private Integer failedCount = 0;
        private Double coveragePercent = 0.0;
        private Boolean coverageAvailable = false;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder analysis(Analysis analysis) { this.analysis = analysis; return this; }
        public Builder testCount(Integer testCount) { this.testCount = testCount; return this; }
        public Builder testClassCount(Integer testClassCount) { this.testClassCount = testClassCount; return this; }
        public Builder passedCount(Integer passedCount) { this.passedCount = passedCount; return this; }
        public Builder failedCount(Integer failedCount) { this.failedCount = failedCount; return this; }
        public Builder coveragePercent(Double coveragePercent) { this.coveragePercent = coveragePercent; return this; }
        public Builder coverageAvailable(Boolean coverageAvailable) { this.coverageAvailable = coverageAvailable; return this; }

        public TestingMetric build() {
            return new TestingMetric(id, analysis, testCount, testClassCount, passedCount, failedCount, coveragePercent, coverageAvailable);
        }
    }
}
