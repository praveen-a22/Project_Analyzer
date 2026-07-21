package com.projectanalyzer.service;

import com.projectanalyzer.dto.report.ReportResponse;
import com.projectanalyzer.entity.Analysis;
import com.projectanalyzer.entity.Project;
import com.projectanalyzer.entity.Report;
import com.projectanalyzer.exception.ResourceNotFoundException;
import com.projectanalyzer.repository.AnalysisRepository;
import com.projectanalyzer.repository.ReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final AnalysisRepository analysisRepository;
    private final ProjectService projectService;

    public ReportService(ReportRepository reportRepository, AnalysisRepository analysisRepository, ProjectService projectService) {
        this.reportRepository = reportRepository;
        this.analysisRepository = analysisRepository;
        this.projectService = projectService;
    }

    @Transactional
    public ReportResponse generateReport(Long projectId) {
        Project project = projectService.getProjectEntityWithOwnershipCheck(projectId);

        Analysis latestAnalysis = analysisRepository.findTopByProjectIdOrderByAnalysisDateDesc(projectId)
                .orElse(null);

        Report report = Report.builder()
                .project(project)
                .analysis(latestAnalysis)
                .reportType("FULL_ASSESSMENT")
                .filePath("reports/project_" + projectId + "_report.pdf")
                .build();

        Report saved = reportRepository.save(report);
        return mapToReportResponse(saved);
    }

    public List<ReportResponse> getProjectReports(Long projectId) {
        projectService.getProjectEntityWithOwnershipCheck(projectId);
        return reportRepository.findByProjectIdOrderByCreatedAtDesc(projectId)
                .stream()
                .map(this::mapToReportResponse)
                .collect(Collectors.toList());
    }

    public ReportResponse getReportById(Long reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new ResourceNotFoundException("Report not found with id: " + reportId));
        projectService.getProjectEntityWithOwnershipCheck(report.getProject().getId());
        return mapToReportResponse(report);
    }

    private ReportResponse mapToReportResponse(Report report) {
        return ReportResponse.builder()
                .id(report.getId())
                .projectId(report.getProject().getId())
                .analysisId(report.getAnalysis() != null ? report.getAnalysis().getId() : null)
                .reportType(report.getReportType())
                .filePath(report.getFilePath())
                .createdAt(report.getCreatedAt())
                .build();
    }
}
