package com.projectanalyzer.dto.project;

import com.projectanalyzer.dto.auth.UserDto;
import com.projectanalyzer.entity.ProjectStatus;

import java.time.LocalDateTime;

public class ProjectResponse {

    private Long id;
    private String name;
    private String description;
    private String githubUrl;
    private String projectPath;
    private ProjectStatus status;
    private Double healthScore;
    private UserDto owner;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastAnalyzedAt;

    public ProjectResponse() {}

    public ProjectResponse(Long id, String name, String description, String githubUrl, String projectPath, ProjectStatus status, Double healthScore, UserDto owner, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime lastAnalyzedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.githubUrl = githubUrl;
        this.projectPath = projectPath;
        this.status = status;
        this.healthScore = healthScore;
        this.owner = owner;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.lastAnalyzedAt = lastAnalyzedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getGithubUrl() { return githubUrl; }
    public void setGithubUrl(String githubUrl) { this.githubUrl = githubUrl; }

    public String getProjectPath() { return projectPath; }
    public void setProjectPath(String projectPath) { this.projectPath = projectPath; }

    public ProjectStatus getStatus() { return status; }
    public void setStatus(ProjectStatus status) { this.status = status; }

    public Double getHealthScore() { return healthScore; }
    public void setHealthScore(Double healthScore) { this.healthScore = healthScore; }

    public UserDto getOwner() { return owner; }
    public void setOwner(UserDto owner) { this.owner = owner; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public LocalDateTime getLastAnalyzedAt() { return lastAnalyzedAt; }
    public void setLastAnalyzedAt(LocalDateTime lastAnalyzedAt) { this.lastAnalyzedAt = lastAnalyzedAt; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String name;
        private String description;
        private String githubUrl;
        private String projectPath;
        private ProjectStatus status;
        private Double healthScore;
        private UserDto owner;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private LocalDateTime lastAnalyzedAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder githubUrl(String githubUrl) { this.githubUrl = githubUrl; return this; }
        public Builder projectPath(String projectPath) { this.projectPath = projectPath; return this; }
        public Builder status(ProjectStatus status) { this.status = status; return this; }
        public Builder healthScore(Double healthScore) { this.healthScore = healthScore; return this; }
        public Builder owner(UserDto owner) { this.owner = owner; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public Builder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }
        public Builder lastAnalyzedAt(LocalDateTime lastAnalyzedAt) { this.lastAnalyzedAt = lastAnalyzedAt; return this; }

        public ProjectResponse build() {
            return new ProjectResponse(id, name, description, githubUrl, projectPath, status, healthScore, owner, createdAt, updatedAt, lastAnalyzedAt);
        }
    }
}
