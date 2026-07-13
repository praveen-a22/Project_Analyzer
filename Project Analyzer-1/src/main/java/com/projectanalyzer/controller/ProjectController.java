package com.projectanalyzer.controller;

import com.projectanalyzer.dto.common.ApiResponse;
import com.projectanalyzer.dto.project.ProjectCreateRequest;
import com.projectanalyzer.dto.project.ProjectResponse;
import com.projectanalyzer.dto.project.ProjectUpdateRequest;
import com.projectanalyzer.entity.Project;
import com.projectanalyzer.service.FileStorageService;
import com.projectanalyzer.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@Tag(name = "Projects", description = "Endpoints for managing user projects and source ZIP uploads")
public class ProjectController {

    private final ProjectService projectService;
    private final FileStorageService fileStorageService;

    public ProjectController(ProjectService projectService, FileStorageService fileStorageService) {
        this.projectService = projectService;
        this.fileStorageService = fileStorageService;
    }

    @PostMapping
    @Operation(summary = "Create a new project")
    public ResponseEntity<ApiResponse<ProjectResponse>> createProject(@Valid @RequestBody ProjectCreateRequest request) {
        ProjectResponse response = projectService.createProject(request);
        return new ResponseEntity<>(ApiResponse.success("Project created successfully", response), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all projects owned by authenticated user")
    public ResponseEntity<ApiResponse<List<ProjectResponse>>> getUserProjects() {
        List<ProjectResponse> projects = projectService.getUserProjects();
        return ResponseEntity.ok(ApiResponse.success(projects));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get project details by ID")
    public ResponseEntity<ApiResponse<ProjectResponse>> getProjectById(@PathVariable Long id) {
        ProjectResponse project = projectService.getProjectById(id);
        return ResponseEntity.ok(ApiResponse.success(project));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update project metadata")
    public ResponseEntity<ApiResponse<ProjectResponse>> updateProject(
            @PathVariable Long id,
            @RequestBody ProjectUpdateRequest request
    ) {
        ProjectResponse response = projectService.updateProject(id, request);
        return ResponseEntity.ok(ApiResponse.success("Project updated successfully", response));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a project")
    public ResponseEntity<ApiResponse<Void>> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.ok(ApiResponse.success("Project deleted successfully", null));
    }

    @PostMapping(value = "/{id}/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload ZIP source archive for static analysis")
    public ResponseEntity<ApiResponse<ProjectResponse>> uploadProjectZip(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file
    ) {
        Project project = fileStorageService.storeProjectZip(id, file);
        ProjectResponse response = projectService.mapToProjectResponse(project);
        return ResponseEntity.ok(ApiResponse.success("Project zip archive uploaded and extracted successfully", response));
    }
}
