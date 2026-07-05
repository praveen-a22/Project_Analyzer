package com.projectanalyzer.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "architecture_metrics")
public class ArchitectureMetric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "analysis_id", nullable = false)
    private Analysis analysis;

    private Integer controllersCount = 0;
    private Integer servicesCount = 0;
    private Integer repositoriesCount = 0;
    private Integer entitiesCount = 0;
    private Double couplingIndex = 0.0;
    private Double cohesionRating = 0.0;
    private Double complexityAvg = 0.0;
    private Double architectureScore = 0.0;

    public ArchitectureMetric() {}

    public ArchitectureMetric(Long id, Analysis analysis, Integer controllersCount, Integer servicesCount, Integer repositoriesCount, Integer entitiesCount, Double couplingIndex, Double cohesionRating, Double complexityAvg, Double architectureScore) {
        this.id = id;
        this.analysis = analysis;
        this.controllersCount = controllersCount != null ? controllersCount : 0;
        this.servicesCount = servicesCount != null ? servicesCount : 0;
        this.repositoriesCount = repositoriesCount != null ? repositoriesCount : 0;
        this.entitiesCount = entitiesCount != null ? entitiesCount : 0;
        this.couplingIndex = couplingIndex != null ? couplingIndex : 0.0;
        this.cohesionRating = cohesionRating != null ? cohesionRating : 0.0;
        this.complexityAvg = complexityAvg != null ? complexityAvg : 0.0;
        this.architectureScore = architectureScore != null ? architectureScore : 0.0;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Analysis getAnalysis() { return analysis; }
    public void setAnalysis(Analysis analysis) { this.analysis = analysis; }

    public Integer getControllersCount() { return controllersCount; }
    public void setControllersCount(Integer controllersCount) { this.controllersCount = controllersCount; }

    public Integer getServicesCount() { return servicesCount; }
    public void setServicesCount(Integer servicesCount) { this.servicesCount = servicesCount; }

    public Integer getRepositoriesCount() { return repositoriesCount; }
    public void setRepositoriesCount(Integer repositoriesCount) { this.repositoriesCount = repositoriesCount; }

    public Integer getEntitiesCount() { return entitiesCount; }
    public void setEntitiesCount(Integer entitiesCount) { this.entitiesCount = entitiesCount; }

    public Double getCouplingIndex() { return couplingIndex; }
    public void setCouplingIndex(Double couplingIndex) { this.couplingIndex = couplingIndex; }

    public Double getCohesionRating() { return cohesionRating; }
    public void setCohesionRating(Double cohesionRating) { this.cohesionRating = cohesionRating; }

    public Double getComplexityAvg() { return complexityAvg; }
    public void setComplexityAvg(Double complexityAvg) { this.complexityAvg = complexityAvg; }

    public Double getArchitectureScore() { return architectureScore; }
    public void setArchitectureScore(Double architectureScore) { this.architectureScore = architectureScore; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private Analysis analysis;
        private Integer controllersCount = 0;
        private Integer servicesCount = 0;
        private Integer repositoriesCount = 0;
        private Integer entitiesCount = 0;
        private Double couplingIndex = 0.0;
        private Double cohesionRating = 0.0;
        private Double complexityAvg = 0.0;
        private Double architectureScore = 0.0;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder analysis(Analysis analysis) { this.analysis = analysis; return this; }
        public Builder controllersCount(Integer controllersCount) { this.controllersCount = controllersCount; return this; }
        public Builder servicesCount(Integer servicesCount) { this.servicesCount = servicesCount; return this; }
        public Builder repositoriesCount(Integer repositoriesCount) { this.repositoriesCount = repositoriesCount; return this; }
        public Builder entitiesCount(Integer entitiesCount) { this.entitiesCount = entitiesCount; return this; }
        public Builder couplingIndex(Double couplingIndex) { this.couplingIndex = couplingIndex; return this; }
        public Builder cohesionRating(Double cohesionRating) { this.cohesionRating = cohesionRating; return this; }
        public Builder complexityAvg(Double complexityAvg) { this.complexityAvg = complexityAvg; return this; }
        public Builder architectureScore(Double architectureScore) { this.architectureScore = architectureScore; return this; }

        public ArchitectureMetric build() {
            return new ArchitectureMetric(id, analysis, controllersCount, servicesCount, repositoriesCount, entitiesCount, couplingIndex, cohesionRating, complexityAvg, architectureScore);
        }
    }
}
