package com.projectanalyzer.service;

import com.projectanalyzer.entity.Project;
import com.projectanalyzer.entity.ProjectStatus;
import com.projectanalyzer.exception.BadRequestException;
import com.projectanalyzer.repository.ProjectRepository;
import com.projectanalyzer.util.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileStorageService {

    private final ProjectService projectService;
    private final ProjectRepository projectRepository;

    @Value("${file.upload-dir:uploads}")
    private String uploadDir;

    public FileStorageService(ProjectService projectService, ProjectRepository projectRepository) {
        this.projectService = projectService;
        this.projectRepository = projectRepository;
    }

    @Transactional
    public Project storeProjectZip(Long projectId, MultipartFile file) {
        Project project = projectService.getProjectEntityWithOwnershipCheck(projectId);

        if (file.isEmpty()) {
            throw new BadRequestException("Uploaded file is empty");
        }

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || !originalFilename.toLowerCase().endsWith(".zip")) {
            throw new BadRequestException("Only .zip archive files are supported");
        }

        File baseStorageDir = new File(uploadDir, "project_" + projectId);
        File destZip = new File(baseStorageDir, "project.zip");
        File extractedDir = new File(baseStorageDir, "extracted");

        try {
            if (baseStorageDir.exists()) {
                FileUtil.deleteDirectory(baseStorageDir);
            }
            baseStorageDir.mkdirs();

            file.transferTo(destZip);

            FileUtil.extractZip(destZip, extractedDir);

            project.setProjectPath(extractedDir.getAbsolutePath());
            project.setStatus(ProjectStatus.UPLOADED);
            return projectRepository.save(project);

        } catch (IOException e) {
            project.setStatus(ProjectStatus.FAILED);
            projectRepository.save(project);
            throw new BadRequestException("Failed to extract project zip archive: " + e.getMessage());
        }
    }
}
