package com.projectanalyzer.controller;

import com.projectanalyzer.dto.ai.AiChatRequest;
import com.projectanalyzer.dto.ai.AiChatResponse;
import com.projectanalyzer.dto.ai.AiSummaryResponse;
import com.projectanalyzer.dto.common.ApiResponse;
import com.projectanalyzer.service.AiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@Tag(name = "AI Intelligence", description = "Endpoints for OpenRouter AI summary, recommendations, and interactive chat")
public class AiController {

    private final AiService aiService;

    public AiController(AiService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/project-summary/{projectId}")
    @Operation(summary = "Get AI-generated project health summary")
    public ResponseEntity<ApiResponse<AiSummaryResponse>> getProjectSummary(@PathVariable Long projectId) {
        AiSummaryResponse summary = aiService.getProjectSummary(projectId);
        return ResponseEntity.ok(ApiResponse.success(summary));
    }

    @PostMapping("/recommendations/{projectId}")
    @Operation(summary = "Get AI-powered refactoring & security recommendations")
    public ResponseEntity<ApiResponse<AiSummaryResponse>> getRecommendations(@PathVariable Long projectId) {
        AiSummaryResponse recommendations = aiService.getRecommendations(projectId);
        return ResponseEntity.ok(ApiResponse.success(recommendations));
    }

    @PostMapping("/chat/{projectId}")
    @Operation(summary = "Ask AI interactive questions about project static analysis findings")
    public ResponseEntity<ApiResponse<AiChatResponse>> chat(
            @PathVariable Long projectId,
            @Valid @RequestBody AiChatRequest request
    ) {
        AiChatResponse response = aiService.chat(projectId, request.getMessage());
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
