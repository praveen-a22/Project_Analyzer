package com.projectanalyzer.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "projects", indexes = {
    @Index(name = "idx_project_owner", columnList = "owner_id")
})
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 255)
    private String githubUrl;

    @Column(length = 255)
    private String projectPath;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ProjectStatus status = ProjectStatus.CREATED;

    private Double healthScore = 0.0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private LocalDateTime lastAnalyzedAt;

    public Project() {}

    public Project(Long id, String name, String description, String githubUrl, String projectPath, ProjectStatus status, Double healthScore, User owner, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime lastAnalyzedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.githubUrl = githubUrl;
        this.projectPath = projectPath;
        this.status = status != null ? status : ProjectStatus.CREATED;
        this.healthScore = healthScore != null ? healthScore : 0.0;
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

    public User getOwner() { return owner; }
    public void setOwner(User owner) { this.owner = owner; }

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
        private ProjectStatus status = ProjectStatus.CREATED;
        private Double healthScore = 0.0;
        private User owner;
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
        public Builder owner(User owner) { this.owner = owner; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public Builder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }
        public Builder lastAnalyzedAt(LocalDateTime lastAnalyzedAt) { this.lastAnalyzedAt = lastAnalyzedAt; return this; }

        public Project build() {
            return new Project(id, name, description, githubUrl, projectPath, status, healthScore, owner, createdAt, updatedAt, lastAnalyzedAt);
        }
    }
}
