package com.projectanalyzer.controller;

import com.projectanalyzer.dto.common.ApiResponse;
import com.projectanalyzer.dto.report.ReportResponse;
import com.projectanalyzer.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Reports", description = "Endpoints for generating and downloading project assessment reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/projects/{id}/reports")
    @Operation(summary = "Generate a new assessment report for a project")
    public ResponseEntity<ApiResponse<ReportResponse>> generateReport(@PathVariable Long id) {
        ReportResponse response = reportService.generateReport(id);
        return new ResponseEntity<>(ApiResponse.success("Report generated successfully", response), HttpStatus.CREATED);
    }

    @GetMapping("/projects/{id}/reports")
    @Operation(summary = "Get all reports for a project")
    public ResponseEntity<ApiResponse<List<ReportResponse>>> getProjectReports(@PathVariable Long id) {
        List<ReportResponse> reports = reportService.getProjectReports(id);
        return ResponseEntity.ok(ApiResponse.success(reports));
    }

    @GetMapping("/reports/{id}")
    @Operation(summary = "Get report details by report ID")
    public ResponseEntity<ApiResponse<ReportResponse>> getReportById(@PathVariable Long id) {
        ReportResponse response = reportService.getReportById(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
