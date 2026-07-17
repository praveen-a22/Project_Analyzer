package com.projectanalyzer.service;

import com.projectanalyzer.dto.architecture.ArchitectureResponse;
import com.projectanalyzer.entity.ArchitectureMetric;
import com.projectanalyzer.exception.ResourceNotFoundException;
import com.projectanalyzer.repository.ArchitectureRepository;
import org.springframework.stereotype.Service;

@Service
public class ArchitectureService {

    private final ArchitectureRepository architectureRepository;
    private final ProjectService projectService;

    public ArchitectureService(ArchitectureRepository architectureRepository, ProjectService projectService) {
        this.architectureRepository = architectureRepository;
        this.projectService = projectService;
    }

    public ArchitectureResponse getProjectArchitecture(Long projectId) {
        projectService.getProjectEntityWithOwnershipCheck(projectId);

        ArchitectureMetric metric = architectureRepository
                .findTopByAnalysisProjectIdOrderByAnalysisAnalysisDateDesc(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("No architecture metrics found for project id: " + projectId));

        return ArchitectureResponse.builder()
                .controllers(metric.getControllersCount())
                .services(metric.getServicesCount())
                .repositories(metric.getRepositoriesCount())
                .entities(metric.getEntitiesCount())
                .coupling(metric.getCouplingIndex())
                .cohesion(metric.getCohesionRating())
                .complexity(metric.getComplexityAvg())
                .architectureScore(metric.getArchitectureScore())
                .build();
    }
}
