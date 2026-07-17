package com.projectanalyzer.controller;

import com.projectanalyzer.dto.analysis.AnalysisResponse;
import com.projectanalyzer.dto.common.ApiResponse;
import com.projectanalyzer.service.AnalysisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Analysis", description = "Endpoints for triggering static analysis and retrieving analysis metrics")
public class AnalysisController {

    private final AnalysisService analysisService;

    public AnalysisController(AnalysisService analysisService) {
        this.analysisService = analysisService;
    }

    @PostMapping("/projects/{id}/analyze")
    @Operation(summary = "Start static code analysis for a project")
    public ResponseEntity<ApiResponse<AnalysisResponse>> analyzeProject(@PathVariable Long id) {
        AnalysisResponse response = analysisService.runAnalysis(id);
        return ResponseEntity.ok(ApiResponse.success("Project analysis completed successfully", response));
    }

    @GetMapping("/projects/{id}/analysis")
    @Operation(summary = "Get latest analysis result for a project")
    public ResponseEntity<ApiResponse<AnalysisResponse>> getLatestAnalysis(@PathVariable Long id) {
        AnalysisResponse response = analysisService.getLatestAnalysis(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/projects/{id}/analysis/history")
    @Operation(summary = "Get analysis execution history for a project")
    public ResponseEntity<ApiResponse<List<AnalysisResponse>>> getAnalysisHistory(@PathVariable Long id) {
        List<AnalysisResponse> history = analysisService.getAnalysisHistory(id);
        return ResponseEntity.ok(ApiResponse.success(history));
    }

    @GetMapping("/analysis/{id}")
    @Operation(summary = "Get specific analysis details by analysis ID")
    public ResponseEntity<ApiResponse<AnalysisResponse>> getAnalysisById(@PathVariable Long id) {
        AnalysisResponse response = analysisService.getAnalysisById(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
