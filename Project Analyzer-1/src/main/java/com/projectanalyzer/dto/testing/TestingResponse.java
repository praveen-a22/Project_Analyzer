package com.projectanalyzer.dto.testing;

public class TestingResponse {

    private Integer testCount;
    private Integer testClassCount;
    private Double coverage;
    private Integer passed;
    private Integer failed;
    private Boolean coverageAvailable;

    public TestingResponse() {}

    public TestingResponse(Integer testCount, Integer testClassCount, Double coverage, Integer passed, Integer failed, Boolean coverageAvailable) {
        this.testCount = testCount;
        this.testClassCount = testClassCount;
        this.coverage = coverage;
        this.passed = passed;
        this.failed = failed;
        this.coverageAvailable = coverageAvailable;
    }

    public Integer getTestCount() { return testCount; }
    public void setTestCount(Integer testCount) { this.testCount = testCount; }

    public Integer getTestClassCount() { return testClassCount; }
    public void setTestClassCount(Integer testClassCount) { this.testClassCount = testClassCount; }

    public Double getCoverage() { return coverage; }
    public void setCoverage(Double coverage) { this.coverage = coverage; }

    public Integer getPassed() { return passed; }
    public void setPassed(Integer passed) { this.passed = passed; }

    public Integer getFailed() { return failed; }
    public void setFailed(Integer failed) { this.failed = failed; }

    public Boolean getCoverageAvailable() { return coverageAvailable; }
    public void setCoverageAvailable(Boolean coverageAvailable) { this.coverageAvailable = coverageAvailable; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Integer testCount;
        private Integer testClassCount;
        private Double coverage;
        private Integer passed;
        private Integer failed;
        private Boolean coverageAvailable;

        public Builder testCount(Integer testCount) { this.testCount = testCount; return this; }
        public Builder testClassCount(Integer testClassCount) { this.testClassCount = testClassCount; return this; }
        public Builder coverage(Double coverage) { this.coverage = coverage; return this; }
        public Builder passed(Integer passed) { this.passed = passed; return this; }
        public Builder failed(Integer failed) { this.failed = failed; return this; }
        public Builder coverageAvailable(Boolean coverageAvailable) { this.coverageAvailable = coverageAvailable; return this; }

        public TestingResponse build() {
            return new TestingResponse(testCount, testClassCount, coverage, passed, failed, coverageAvailable);
        }
    }
}
