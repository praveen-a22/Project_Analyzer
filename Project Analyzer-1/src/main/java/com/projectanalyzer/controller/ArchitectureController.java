package com.projectanalyzer.controller;

import com.projectanalyzer.dto.architecture.ArchitectureResponse;
import com.projectanalyzer.dto.common.ApiResponse;
import com.projectanalyzer.service.ArchitectureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Tag(name = "Architecture", description = "Endpoints for retrieving system architecture and layer distribution metrics")
public class ArchitectureController {

    private final ArchitectureService architectureService;

    public ArchitectureController(ArchitectureService architectureService) {
        this.architectureService = architectureService;
    }

    @GetMapping("/projects/{id}/architecture")
    @Operation(summary = "Get architecture metrics for a project")
    public ResponseEntity<ApiResponse<ArchitectureResponse>> getProjectArchitecture(@PathVariable Long id) {
        ArchitectureResponse response = architectureService.getProjectArchitecture(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
