package com.projectanalyzer.service;

import com.projectanalyzer.dto.testing.TestingResponse;
import com.projectanalyzer.entity.TestingMetric;
import com.projectanalyzer.exception.ResourceNotFoundException;
import com.projectanalyzer.repository.TestingRepository;
import org.springframework.stereotype.Service;

@Service
public class TestingService {

    private final TestingRepository testingRepository;
    private final ProjectService projectService;

    public TestingService(TestingRepository testingRepository, ProjectService projectService) {
        this.testingRepository = testingRepository;
        this.projectService = projectService;
    }

    public TestingResponse getProjectTesting(Long projectId) {
        projectService.getProjectEntityWithOwnershipCheck(projectId);

        TestingMetric metric = testingRepository
                .findTopByAnalysisProjectIdOrderByAnalysisAnalysisDateDesc(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("No testing metrics found for project id: " + projectId));

        return TestingResponse.builder()
                .testCount(metric.getTestCount())
                .testClassCount(metric.getTestClassCount())
                .coverage(metric.getCoveragePercent())
                .passed(metric.getPassedCount())
                .failed(metric.getFailedCount())
                .coverageAvailable(metric.getCoverageAvailable())
                .build();
    }
}
