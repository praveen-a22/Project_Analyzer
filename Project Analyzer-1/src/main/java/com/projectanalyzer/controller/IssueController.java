package com.projectanalyzer.controller;

import com.projectanalyzer.dto.common.ApiResponse;
import com.projectanalyzer.dto.issue.IssueResponse;
import com.projectanalyzer.dto.issue.IssueStatusUpdateRequest;
import com.projectanalyzer.entity.IssueCategory;
import com.projectanalyzer.entity.IssueStatus;
import com.projectanalyzer.entity.Severity;
import com.projectanalyzer.service.IssueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Issues", description = "Endpoints for retrieving code issues and updating status")
public class IssueController {

    private final IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @GetMapping("/projects/{id}/issues")
    @Operation(summary = "Get issues for a project with optional filters")
    public ResponseEntity<ApiResponse<List<IssueResponse>>> getProjectIssues(
            @PathVariable Long id,
            @RequestParam(required = false) Severity severity,
            @RequestParam(required = false) IssueCategory category,
            @RequestParam(required = false) IssueStatus status
    ) {
        List<IssueResponse> issues = issueService.getProjectIssues(id, severity, category, status);
        return ResponseEntity.ok(ApiResponse.success(issues));
    }

    @GetMapping("/issues/{id}")
    @Operation(summary = "Get issue details by issue ID")
    public ResponseEntity<ApiResponse<IssueResponse>> getIssueById(@PathVariable Long id) {
        IssueResponse issue = issueService.getIssueById(id);
        return ResponseEntity.ok(ApiResponse.success(issue));
    }

    @PatchMapping("/issues/{id}/status")
    @Operation(summary = "Update issue resolution status")
    public ResponseEntity<ApiResponse<IssueResponse>> updateIssueStatus(
            @PathVariable Long id,
            @Valid @RequestBody IssueStatusUpdateRequest request
    ) {
        IssueResponse response = issueService.updateIssueStatus(id, request);
        return ResponseEntity.ok(ApiResponse.success("Issue status updated", response));
    }
}
