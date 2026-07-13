package com.projectanalyzer.service;

import com.projectanalyzer.dto.project.ProjectCreateRequest;
import com.projectanalyzer.dto.project.ProjectResponse;
import com.projectanalyzer.entity.Project;
import com.projectanalyzer.entity.ProjectStatus;
import com.projectanalyzer.entity.User;
import com.projectanalyzer.exception.UnauthorizedException;
import com.projectanalyzer.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private AuthService authService;

    @InjectMocks
    private ProjectService projectService;

    private User userOwner;
    private User userOther;
    private Project sampleProject;

    @BeforeEach
    void setUp() {
        userOwner = User.builder().id(1L).name("User Owner").email("owner@example.com").build();
        userOther = User.builder().id(2L).name("User Other").email("other@example.com").build();

        sampleProject = Project.builder()
                .id(10L)
                .name("Test Project")
                .description("Sample description")
                .status(ProjectStatus.CREATED)
                .healthScore(85.0)
                .owner(userOwner)
                .build();
    }

    @Test
    void createProject_Successful() {
        ProjectCreateRequest request = new ProjectCreateRequest();
        request.setName("Test Project");
        request.setDescription("Sample description");

        when(authService.getCurrentAuthenticatedUser()).thenReturn(userOwner);
        when(projectRepository.save(any(Project.class))).thenReturn(sampleProject);

        ProjectResponse response = projectService.createProject(request);

        assertNotNull(response);
        assertEquals("Test Project", response.getName());
        verify(projectRepository, times(1)).save(any(Project.class));
    }

    @Test
    void getProjectById_UnauthorizedUser_ThrowsUnauthorizedException() {
        when(authService.getCurrentAuthenticatedUser()).thenReturn(userOther);
        when(projectRepository.findById(10L)).thenReturn(Optional.of(sampleProject));

        assertThrows(UnauthorizedException.class, () -> projectService.getProjectById(10L));
    }
}
