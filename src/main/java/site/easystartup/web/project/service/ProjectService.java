package site.easystartup.web.project.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import site.easystartup.web.domain.User;
import site.easystartup.web.project.domain.exeption.ProjectNotFoundException;
import site.easystartup.web.project.domain.model.Participant;
import site.easystartup.web.project.domain.model.Project;
import site.easystartup.web.project.domain.payload.requst.ProjectRequest;
import site.easystartup.web.project.repo.ParticipantRepo;
import site.easystartup.web.project.repo.ProjectRepo;
import site.easystartup.web.service.UserService;
import site.easystartup.web.storage.service.StorageService;

import java.security.Principal;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepo projectRepo;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final StorageService storageService;
    private final ParticipantRepo participantRepo;


    public Project createProject(ProjectRequest projectRequest, Principal principal) {
        Project project = projectRequestToProject(projectRequest, principal);
        return projectRepo.save(project);
    }

    public Project editProject(ProjectRequest projectRequest, Long projectId, Principal principal) {
        if (!projectIsBelongUser(getProjectById(projectId), principal))
            throw  new RuntimeException("This project cannot be changed");

        Project project = projectRequestToProject(projectRequest, principal);
        project.setProjectId(projectId);

        return projectRepo.save(project);
    }

    public void deleteProject(Long projectId, Principal principal) {
        Project project = getProjectById(projectId);
        if (!projectIsBelongUser(project, principal))
            throw  new RuntimeException("This project cannot be deleted");

        projectRepo.delete(project);
    }

    public Project getProjectById(Long projectId) {
        return projectRepo.findById(projectId).orElseThrow(() -> new ProjectNotFoundException("Project not found"));
    }

    public List<Project> getAllProjectsWithCommercialStatus(int commercialStatus) {
        return projectRepo.findAllByCommercialStatus(commercialStatus);
    }

    public List<Project> getAllProjectsForUser(Long userId) {
        User user = userService.getUserById(userId);
        return projectRepo.findAllByOwner(user);
    }

    public List<Project> getAllProjectsWithTechnology(String technology) {
        return projectRepo.findAllByTechnology(technology.trim());
    }

    public List<Project> getAllProjectsForCurrentUser(Principal principal) {
        return projectRepo.findAllByOwner(userService.getUserByPrincipal(principal));
    }

    public LinkedHashSet<Project> getAllProjectsWithPosition(String nameOfPosition) {
        return participantRepo.findAllByNameOfPosition(nameOfPosition.trim());
    }

    public Project applayOnProject(Long projectId, String nameOfPosition, Principal principal) {
        Project project = getProjectById(projectId);
        project.getParticipants().stream().forEach(participant -> {
            if (participant.getNameOfPosition().equals(nameOfPosition))
                participant.getRequests().add(userService.getUserByPrincipal(principal));
        });

        return projectRepo.save(project);
    }

    public List<Participant> getAllRequestsForProject(Long projectId) {
        Project project = getProjectById(projectId);
        return project.getParticipants();
    }

    public Object confirmParticipant(Long projectId, String nameOfPosition, Long participantId, Principal principal) {
        Project project = getProjectById(projectId);

        if (!projectIsBelongUser(project, principal))
            throw  new RuntimeException("This project cannot be changed");

        User user = userService.getUserById(participantId);
        project.getParticipants().forEach(participant -> {
            if (participant.getNameOfPosition().equals(nameOfPosition)) {
                participant.setUser(user);
                participant.getRequests().remove(user);
            }
        });
        return projectRepo.save(project);
    }


    private String formatTechnology(String technology) {
        StringBuilder result = new StringBuilder();
        Arrays.stream(technology.split(",")).forEach(tech -> result.append(tech.trim()).append(" "));
        return result.toString();
    }

    private Project projectRequestToProject(ProjectRequest projectRequest, Principal principal) {
        Project project = new Project();

        project.setParticipants(projectRequest.getParticipants()
                .stream().map(part -> modelMapper.map(part, Participant.class)).collect(Collectors.toList()));
        project.setTitle(projectRequest.getTitle());
        project.setDescription(projectRequest.getDescription());
        project.setTechnology(formatTechnology(projectRequest.getTechnology()));
        project.setCommercialStatus(project.getCommercialStatus());
        project.setCoverLink(projectRequest.getCover().getName());
        project.setOwner(userService.getUserByPrincipal(principal));
        storageService.save(projectRequest.getCover());

        return project;
    }

    private boolean projectIsBelongUser(Project project, Principal principal) {
        return project.getOwner().equals(userService.getUserByPrincipal(principal));
    }
}
