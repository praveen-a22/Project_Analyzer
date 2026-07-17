package com.projectanalyzer.service;

import com.projectanalyzer.dto.dependency.DependencyResponse;
import com.projectanalyzer.entity.Dependency;
import com.projectanalyzer.exception.ResourceNotFoundException;
import com.projectanalyzer.repository.DependencyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DependencyService {

    private final DependencyRepository dependencyRepository;
    private final ProjectService projectService;

    public DependencyService(DependencyRepository dependencyRepository, ProjectService projectService) {
        this.dependencyRepository = dependencyRepository;
        this.projectService = projectService;
    }

    public List<DependencyResponse> getProjectDependencies(Long projectId) {
        projectService.getProjectEntityWithOwnershipCheck(projectId);
        return dependencyRepository.findByAnalysisProjectId(projectId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public DependencyResponse getDependencyById(Long id) {
        Dependency dependency = dependencyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dependency not found with id: " + id));
        projectService.getProjectEntityWithOwnershipCheck(dependency.getAnalysis().getProject().getId());
        return mapToResponse(dependency);
    }

    private DependencyResponse mapToResponse(Dependency dep) {
        return DependencyResponse.builder()
                .id(dep.getId())
                .analysisId(dep.getAnalysis().getId())
                .name(dep.getName())
                .version(dep.getVersion())
                .latestVersion(dep.getLatestVersion())
                .status(dep.getStatus())
                .severity(dep.getSeverity())
                .build();
    }
}
