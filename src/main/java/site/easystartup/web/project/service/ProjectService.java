package site.easystartup.web.project.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import site.easystartup.web.project.domain.model.Participant;
import site.easystartup.web.project.domain.model.Project;
import site.easystartup.web.project.domain.payload.requst.ProjectRequest;
import site.easystartup.web.project.repo.ProjectRepo;
import site.easystartup.web.service.UserService;
import site.easystartup.web.storage.service.StorageService;

import java.security.Principal;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepo projectRepo;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final StorageService storageService;


    public Project createProject(ProjectRequest projectRequest, Principal principal) {
        Project project = projectRequestToProject(projectRequest);

        project.setOwner(userService.getUserByPrincipal(principal));
        return project;
    }

    public Project updateProject(Project projectUpdate, Long projectId, Principal principal) {
    }

    public void deleteProject(Long projectId, Principal principal) {
    }

    public Project getProjectById(Long projectId) {
    }

    public Set<Project> getAllProjectsForUser(Long userId) {
        
        
    }

    public Set<Project> getAllProjectsWithTechnology(String technology) {
    }

    public Set<Project> getAllProjectsForCurrentUser(Principal principal) {
    }

    public Set<Project> getAllProjectsWithPosition(String nameOfPosition) {
    }

    public Object applayOnProject(Long projectId, Principal principal) {
    }

    public Object confirmParticipant(Long projectId, Long participantId, Principal principal) {
    }


    private String formatTechnology(String technology) {
        StringBuilder result = new StringBuilder();
        Arrays.stream(technology.split(",")).forEach(tech -> result.append(tech.trim()).append(" "));
        return result.toString();
    }

    private Project projectRequestToProject(ProjectRequest projectRequest) {
        Project project = new Project();

        project.setParticipants(projectRequest.getParticipants()
                .stream().map(part -> modelMapper.map(part, Participant.class)).collect(Collectors.toList()));
        project.setTitle(projectRequest.getTitle());
        project.setDescription(projectRequest.getDescription());
        project.setTechnology(formatTechnology(projectRequest.getTechnology()));
        project.setCommercialStatus(project.getCommercialStatus());
        project.setCoverLink(projectRequest.getCover().getName());
        storageService.save(projectRequest.getCover());

        return project;
    }
}
