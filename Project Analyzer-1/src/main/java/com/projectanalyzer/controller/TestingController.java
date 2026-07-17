package com.projectanalyzer.controller;

import com.projectanalyzer.dto.common.ApiResponse;
import com.projectanalyzer.dto.testing.TestingResponse;
import com.projectanalyzer.service.TestingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Tag(name = "Testing", description = "Endpoints for retrieving unit testing and code coverage metrics")
public class TestingController {

    private final TestingService testingService;

    public TestingController(TestingService testingService) {
        this.testingService = testingService;
    }

    @GetMapping("/projects/{id}/testing")
    @Operation(summary = "Get testing metrics and test coverage for a project")
    public ResponseEntity<ApiResponse<TestingResponse>> getProjectTesting(@PathVariable Long id) {
        TestingResponse response = testingService.getProjectTesting(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
