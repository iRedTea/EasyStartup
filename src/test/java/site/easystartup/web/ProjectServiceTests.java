package site.easystartup.web;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import site.easystartup.web.project.domain.model.Project;
import site.easystartup.web.project.domain.requst.ProjectRequest;
import site.easystartup.web.project.repo.ProjectRepo;
import site.easystartup.web.project.service.ProjectService;

import java.security.Principal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class ProjectServiceTests {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectRepo projectRepo;

    private Principal principal;

    private Project project;

    @BeforeEach
    public void onStart() {
        Mockito.doReturn("testUser").when(principal).getName();

        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "hello",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello file".getBytes()
        );

        ProjectRequest projectRequest = new ProjectRequest();
        projectRequest.setDescription("testDesc");
        projectRequest.setTitle("testTitle");
        //projectRequest.setCover(file);
        projectRequest.setTechnology(new ArrayList<>());
        project = projectService.createProject(projectRequest, principal);
    }

    @Test
    public void shouldAllProjects() {
        assertTrue(projectRepo.findAll().stream().anyMatch(p -> p.getTitle().equals("testTitle")));
    }

    @AfterEach
    public void onEnd() {
        projectRepo.delete(project);
    }


}
