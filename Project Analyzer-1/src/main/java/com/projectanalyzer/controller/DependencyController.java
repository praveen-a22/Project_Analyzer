package com.projectanalyzer.controller;

import com.projectanalyzer.dto.common.ApiResponse;
import com.projectanalyzer.dto.dependency.DependencyResponse;
import com.projectanalyzer.service.DependencyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Dependencies", description = "Endpoints for retrieving project dependency vulnerability audits")
public class DependencyController {

    private final DependencyService dependencyService;

    public DependencyController(DependencyService dependencyService) {
        this.dependencyService = dependencyService;
    }

    @GetMapping("/projects/{id}/dependencies")
    @Operation(summary = "Get dependencies for a project")
    public ResponseEntity<ApiResponse<List<DependencyResponse>>> getProjectDependencies(@PathVariable Long id) {
        List<DependencyResponse> dependencies = dependencyService.getProjectDependencies(id);
        return ResponseEntity.ok(ApiResponse.success(dependencies));
    }

    @GetMapping("/dependencies/{id}")
    @Operation(summary = "Get dependency detail by ID")
    public ResponseEntity<ApiResponse<DependencyResponse>> getDependencyById(@PathVariable Long id) {
        DependencyResponse response = dependencyService.getDependencyById(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
