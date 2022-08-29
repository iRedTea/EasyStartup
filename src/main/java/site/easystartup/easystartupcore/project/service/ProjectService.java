package site.easystartup.easystartupcore.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.easystartup.easystartupcore.project.domain.model.Project;
import site.easystartup.easystartupcore.project.repo.ProjectRepo;
import site.easystartup.easystartupcore.service.UserService;

import java.security.Principal;
import java.util.Arrays;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepo projectRepo;
    private final UserService userService;


    public Project createProject(Project project, Principal principal) {

        project.setTechnology(technologyToString(project.getTechnology()));
        project.setOwner(userService.getUserByPrincipal(principal));

        return projectRepo.save(project);
    }
//
//    public Project updateProject(Project map, Long projectId, Principal principal) {
//    }
//
//    public void deleteProject(Long projectId, Principal principal) {
//    }
//
//    public Project getProjectById(Long projectId) {
//    }
//
//    public Set<Project> getAllProjectsForUser(Long userId) {
//
//
//    }
//
//    public Set<Project> getAllProjectsWithTechnology(String technology) {
//    }
//
//    public Set<Project> getAllProjectsForCurrentUser(Principal principal) {
//    }
//
//    public Set<Project> getAllProjectsWithPosition(String nameOfPosition) {
//    }
//
//    public Object applayOnProject(Long projectId, Principal principal) {
//    }
//
//    public Object confirmParticipant(Long projectId, Long participantId, Principal principal) {
//    }




    private String technologyToString(String technology) {
        StringBuilder technologyResult = new StringBuilder();
        Arrays.stream(technology.split(",")).forEach(tech -> technologyResult.append(tech.trim() + " "));
        return technologyResult.toString();
    }
}
