package com.projectanalyzer.service;

import com.projectanalyzer.dto.issue.IssueResponse;
import com.projectanalyzer.dto.issue.IssueStatusUpdateRequest;
import com.projectanalyzer.entity.Issue;
import com.projectanalyzer.entity.IssueCategory;
import com.projectanalyzer.entity.IssueStatus;
import com.projectanalyzer.entity.Severity;
import com.projectanalyzer.exception.ResourceNotFoundException;
import com.projectanalyzer.repository.IssueRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IssueService {

    private final IssueRepository issueRepository;
    private final ProjectService projectService;

    public IssueService(IssueRepository issueRepository, ProjectService projectService) {
        this.issueRepository = issueRepository;
        this.projectService = projectService;
    }

    public List<IssueResponse> getProjectIssues(
            Long projectId,
            Severity severity,
            IssueCategory category,
            IssueStatus status
    ) {
        projectService.getProjectEntityWithOwnershipCheck(projectId);

        List<Issue> issues = issueRepository.filterIssues(projectId, severity, category, status);
        return issues.stream()
                .map(this::mapToIssueResponse)
                .collect(Collectors.toList());
    }

    public IssueResponse getIssueById(Long issueId) {
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new ResourceNotFoundException("Issue not found with id: " + issueId));
        projectService.getProjectEntityWithOwnershipCheck(issue.getAnalysis().getProject().getId());
        return mapToIssueResponse(issue);
    }

    @Transactional
    public IssueResponse updateIssueStatus(Long issueId, IssueStatusUpdateRequest request) {
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new ResourceNotFoundException("Issue not found with id: " + issueId));
        projectService.getProjectEntityWithOwnershipCheck(issue.getAnalysis().getProject().getId());

        issue.setStatus(request.getStatus());
        Issue updated = issueRepository.save(issue);
        return mapToIssueResponse(updated);
    }

    public IssueResponse mapToIssueResponse(Issue issue) {
        return IssueResponse.builder()
                .id(issue.getId())
                .analysisId(issue.getAnalysis().getId())
                .severity(issue.getSeverity())
                .category(issue.getCategory())
                .title(issue.getTitle())
                .description(issue.getDescription())
                .filePath(issue.getFilePath())
                .lineNumber(issue.getLineNumber())
                .status(issue.getStatus())
                .recommendation(issue.getRecommendation())
                .createdAt(issue.getCreatedAt())
                .build();
    }
}
