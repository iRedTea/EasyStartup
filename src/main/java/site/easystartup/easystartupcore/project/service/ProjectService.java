package site.easystartup.easystartupcore.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.easystartup.easystartupcore.project.domain.model.Project;
import site.easystartup.easystartupcore.project.repo.ProjectRepo;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepo projectRepo;


    public Project createProject(Project project, Principal principal) {
        return null;
    }
}
