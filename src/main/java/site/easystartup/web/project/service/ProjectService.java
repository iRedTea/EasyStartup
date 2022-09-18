package site.easystartup.web.project.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import site.easystartup.web.domain.model.User;
import site.easystartup.web.project.domain.exception.ProjectNotFoundException;
import site.easystartup.web.project.domain.model.Participant;
import site.easystartup.web.project.domain.model.Project;
import site.easystartup.web.project.domain.requst.ProjectRequest;
import site.easystartup.web.project.repo.ParticipantRepo;
import site.easystartup.web.project.repo.ProjectRepo;
import site.easystartup.web.service.UserService;
import site.easystartup.web.storage.service.StorageService;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepo projectRepo;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final StorageService storageService;
    private final ParticipantRepo participantRepo;
    private final TagService tagService;



    public Project createProject(ProjectRequest projectRequest, Principal principal) {
        Project project = projectRepo.save(projectRequestToProject(projectRequest, userService.getCurrentUsername()));
        project.setParticipants(getParticipantForProject(projectRequest, project));
        return projectRepo.save(project);
    }

    public Project editProject(ProjectRequest projectRequest, Long projectId) {
        Project projectOld = getProjectById(projectId);
        if (!projectIsBelongUser(projectOld, userService.getCurrentUsername()))
            throw  new RuntimeException("This project cannot be changed");

        Project project = projectRequestToProject(projectRequest, userService.getCurrentUsername());
        project.setParticipants(projectOld.getParticipants());
        project.setProjectId(projectId);

        return projectRepo.save(project);
    }

    private List<Participant> getParticipantForProject(ProjectRequest projectRequest, Project project) {
        List<Participant> part = new ArrayList<>();
        projectRequest.getParticipants().forEach(participantDto -> {
            Participant participant = new Participant();
            participant.setProject(project);
            participant.setNameOfPosition(participantDto.getNameOfPosition());
            part.add(participant);
        });
        return part;
    }

    public void deleteProject(Long projectId) {
        Project project = getProjectById(projectId);
        if (!projectIsBelongUser(project, userService.getCurrentUsername()))
            throw  new RuntimeException("This project cannot be deleted");

        projectRepo.delete(project);
    }

    public Project getProjectById(Long projectId) {
        return projectRepo.findById(projectId).orElseThrow(() -> new ProjectNotFoundException("Project not found"));
    }

    public List<Project> getAllProjectsWithCommercialStatus(int commercialStatus) {
        return projectRepo.findAllByCommercialStatusOrderByCreatedDateAsc(commercialStatus);
    }

    public List<Project> getAllProjectsForUser(Long userId) {
        User user = userService.getUserById(userId);
        return projectRepo.findAllByOwnerOrderByCreatedDate(user);
    }

    public List<Project> getAllProjectsWithTechnology(String technology) {
        return projectRepo.findAllByTechnology(tagService.getTechnologyByName(technology.trim()));
    }

    public List<Project> getAllProjectsForCurrentUser() {
        return projectRepo.findAllByOwnerOrderByCreatedDate(userService.getCurrentUser());
    }

    public LinkedHashSet<Project> getAllProjectsWithPosition(String nameOfPosition) {
        List<Participant> parts = participantRepo.findAllByNameOfPositionIgnoreCase(nameOfPosition.trim());
        LinkedHashSet<Project> projects = new LinkedHashSet<>();
        parts.forEach(part -> projects.add(part.getProject()));

        return projects;
    }

    public Project applyOnProject(Long projectId, String nameOfPosition) {
        Project project = getProjectById(projectId);
        project.getParticipants().stream().forEach(participant -> {
            if (participant.getNameOfPosition().equals(nameOfPosition))
                participant.getRequests().add(userService.getCurrentUser());
        });

        return projectRepo.save(project);
    }

    public List<Participant> getAllRequestsForProject(Long projectId) {
        Project project = getProjectById(projectId);
        return project.getParticipants();
    }

    public Project confirmParticipant(Long projectId, String nameOfPosition, Long participantId, String principal) {
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
        projectRepo.save(project);
        return projectRepo.save(project);
    }

    private Project projectRequestToProject(ProjectRequest projectRequest, String principal) {
        Project project = new Project();

        project.setTitle(projectRequest.getTitle());
        project.setDescription(projectRequest.getDescription());
        project.setTechnology(tagService.convertTechnology(projectRequest.getTechnology()));
        project.setCommercialStatus(project.getCommercialStatus());
//        project.setCoverLink(projectRequest.getCover().getName());
        project.setOwner(userService.getUserByUsername(principal));
        return project;
    }

    public boolean projectIsBelongUser(Project project, String principal) {
        return project.getOwner().equals(userService.getUserByUsername(principal));
    }
}
