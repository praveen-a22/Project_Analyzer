package com.projectanalyzer.dto.architecture;

public class ArchitectureResponse {

    private Integer controllers;
    private Integer services;
    private Integer repositories;
    private Integer entities;
    private Double coupling;
    private Double cohesion;
    private Double complexity;
    private Double architectureScore;

    public ArchitectureResponse() {}

    public ArchitectureResponse(Integer controllers, Integer services, Integer repositories, Integer entities, Double coupling, Double cohesion, Double complexity, Double architectureScore) {
        this.controllers = controllers;
        this.services = services;
        this.repositories = repositories;
        this.entities = entities;
        this.coupling = coupling;
        this.cohesion = cohesion;
        this.complexity = complexity;
        this.architectureScore = architectureScore;
    }

    public Integer getControllers() { return controllers; }
    public void setControllers(Integer controllers) { this.controllers = controllers; }

    public Integer getServices() { return services; }
    public void setServices(Integer services) { this.services = services; }

    public Integer getRepositories() { return repositories; }
    public void setRepositories(Integer repositories) { this.repositories = repositories; }

    public Integer getEntities() { return entities; }
    public void setEntities(Integer entities) { this.entities = entities; }

    public Double getCoupling() { return coupling; }
    public void setCoupling(Double coupling) { this.coupling = coupling; }

    public Double getCohesion() { return cohesion; }
    public void setCohesion(Double cohesion) { this.cohesion = cohesion; }

    public Double getComplexity() { return complexity; }
    public void setComplexity(Double complexity) { this.complexity = complexity; }

    public Double getArchitectureScore() { return architectureScore; }
    public void setArchitectureScore(Double architectureScore) { this.architectureScore = architectureScore; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Integer controllers;
        private Integer services;
        private Integer repositories;
        private Integer entities;
        private Double coupling;
        private Double cohesion;
        private Double complexity;
        private Double architectureScore;

        public Builder controllers(Integer controllers) { this.controllers = controllers; return this; }
        public Builder services(Integer services) { this.services = services; return this; }
        public Builder repositories(Integer repositories) { this.repositories = repositories; return this; }
        public Builder entities(Integer entities) { this.entities = entities; return this; }
        public Builder coupling(Double coupling) { this.coupling = coupling; return this; }
        public Builder cohesion(Double cohesion) { this.cohesion = cohesion; return this; }
        public Builder complexity(Double complexity) { this.complexity = complexity; return this; }
        public Builder architectureScore(Double architectureScore) { this.architectureScore = architectureScore; return this; }

        public ArchitectureResponse build() {
            return new ArchitectureResponse(controllers, services, repositories, entities, coupling, cohesion, complexity, architectureScore);
        }
    }
}
