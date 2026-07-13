package com.projectanalyzer.service;

import com.projectanalyzer.dto.project.ProjectCreateRequest;
import com.projectanalyzer.dto.project.ProjectResponse;
import com.projectanalyzer.dto.project.ProjectUpdateRequest;
import com.projectanalyzer.entity.Project;
import com.projectanalyzer.entity.ProjectStatus;
import com.projectanalyzer.entity.User;
import com.projectanalyzer.exception.ResourceNotFoundException;
import com.projectanalyzer.exception.UnauthorizedException;
import com.projectanalyzer.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final AuthService authService;

    public ProjectService(ProjectRepository projectRepository, AuthService authService) {
        this.projectRepository = projectRepository;
        this.authService = authService;
    }

    @Transactional
    public ProjectResponse createProject(ProjectCreateRequest request) {
        User currentUser = authService.getCurrentAuthenticatedUser();

        Project project = Project.builder()
                .name(request.getName())
                .description(request.getDescription())
                .githubUrl(request.getGithubUrl())
                .status(ProjectStatus.CREATED)
                .healthScore(0.0)
                .owner(currentUser)
                .build();

        Project savedProject = projectRepository.save(project);
        return mapToProjectResponse(savedProject);
    }

    public List<ProjectResponse> getUserProjects() {
        User currentUser = authService.getCurrentAuthenticatedUser();
        return projectRepository.findByOwnerIdOrderByCreatedAtDesc(currentUser.getId())
                .stream()
                .map(this::mapToProjectResponse)
                .collect(Collectors.toList());
    }

    public ProjectResponse getProjectById(Long id) {
        Project project = getProjectEntityWithOwnershipCheck(id);
        return mapToProjectResponse(project);
    }

    @Transactional
    public ProjectResponse updateProject(Long id, ProjectUpdateRequest request) {
        Project project = getProjectEntityWithOwnershipCheck(id);

        if (request.getName() != null && !request.getName().isBlank()) {
            project.setName(request.getName());
        }
        if (request.getDescription() != null) {
            project.setDescription(request.getDescription());
        }
        if (request.getGithubUrl() != null) {
            project.setGithubUrl(request.getGithubUrl());
        }

        Project updatedProject = projectRepository.save(project);
        return mapToProjectResponse(updatedProject);
    }

    @Transactional
    public void deleteProject(Long id) {
        Project project = getProjectEntityWithOwnershipCheck(id);
        projectRepository.delete(project);
    }

    public Project getProjectEntityWithOwnershipCheck(Long id) {
        User currentUser = authService.getCurrentAuthenticatedUser();
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));

        if (!project.getOwner().getId().equals(currentUser.getId())) {
            throw new UnauthorizedException("You do not have permission to access this project");
        }
        return project;
    }

    public ProjectResponse mapToProjectResponse(Project project) {
        return ProjectResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .githubUrl(project.getGithubUrl())
                .projectPath(project.getProjectPath())
                .status(project.getStatus())
                .healthScore(project.getHealthScore())
                .owner(authService.mapToUserDto(project.getOwner()))
                .createdAt(project.getCreatedAt())
                .updatedAt(project.getUpdatedAt())
                .lastAnalyzedAt(project.getLastAnalyzedAt())
                .build();
    }
}
