package com.projectanalyzer.dto.project;

public class ProjectUpdateRequest {
    private String name;
    private String description;
    private String githubUrl;

    public ProjectUpdateRequest() {}

    public ProjectUpdateRequest(String name, String description, String githubUrl) {
        this.name = name;
        this.description = description;
        this.githubUrl = githubUrl;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getGithubUrl() { return githubUrl; }
    public void setGithubUrl(String githubUrl) { this.githubUrl = githubUrl; }
}
