package com.projectanalyzer.dto.ai;

import java.util.List;

public class AiSummaryResponse {
    private String summary;
    private List<String> majorProblems;
    private List<String> recommendations;
    private List<String> priorityImprovements;

    public AiSummaryResponse() {}

    public AiSummaryResponse(String summary, List<String> majorProblems, List<String> recommendations, List<String> priorityImprovements) {
        this.summary = summary;
        this.majorProblems = majorProblems;
        this.recommendations = recommendations;
        this.priorityImprovements = priorityImprovements;
    }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public List<String> getMajorProblems() { return majorProblems; }
    public void setMajorProblems(List<String> majorProblems) { this.majorProblems = majorProblems; }

    public List<String> getRecommendations() { return recommendations; }
    public void setRecommendations(List<String> recommendations) { this.recommendations = recommendations; }

    public List<String> getPriorityImprovements() { return priorityImprovements; }
    public void setPriorityImprovements(List<String> priorityImprovements) { this.priorityImprovements = priorityImprovements; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String summary;
        private List<String> majorProblems;
        private List<String> recommendations;
        private List<String> priorityImprovements;

        public Builder summary(String summary) { this.summary = summary; return this; }
        public Builder majorProblems(List<String> majorProblems) { this.majorProblems = majorProblems; return this; }
        public Builder recommendations(List<String> recommendations) { this.recommendations = recommendations; return this; }
        public Builder priorityImprovements(List<String> priorityImprovements) { this.priorityImprovements = priorityImprovements; return this; }

        public AiSummaryResponse build() {
            return new AiSummaryResponse(summary, majorProblems, recommendations, priorityImprovements);
        }
    }
}
