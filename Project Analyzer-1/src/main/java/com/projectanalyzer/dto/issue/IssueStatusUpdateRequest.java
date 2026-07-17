package com.projectanalyzer.dto.issue;

import com.projectanalyzer.entity.IssueStatus;
import jakarta.validation.constraints.NotNull;

public class IssueStatusUpdateRequest {

    @NotNull(message = "Status is required")
    private IssueStatus status;

    public IssueStatusUpdateRequest() {}

    public IssueStatusUpdateRequest(IssueStatus status) {
        this.status = status;
    }

    public IssueStatus getStatus() { return status; }
    public void setStatus(IssueStatus status) { this.status = status; }
}
