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

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepo projectRepo;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final StorageService storageService;
    private final ParticipantRepo participantRepo;
    private final TechnologyService technologyService;



    public Project createProject(ProjectRequest projectRequest, Principal principal) {
        Project project = projectRepo.save(projectRequestToProject(projectRequest, principal));
        project.setParticipants(getParticipantForProject(projectRequest, project));
        return projectRepo.save(project);
    }

    public Project editProject(ProjectRequest projectRequest, Long projectId, Principal principal) {
        Project projectOld = getProjectById(projectId);
        if (!projectIsBelongUser(projectOld, principal))
            throw  new RuntimeException("This project cannot be changed");

        Project project = projectRequestToProject(projectRequest, principal);
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
        return projectRepo.findAllByCommercialStatusOrderByCreatedDateAsc(commercialStatus);
    }

    public List<Project> getAllProjectsForUser(Long userId) {
        User user = userService.getUserById(userId);
        return projectRepo.findAllByOwnerOrderByCreatedDate(user);
    }

    public List<Project> getAllProjects() {
        return projectRepo.findAll();
    }

    public List<Project> getAllProjectsWithTechnology(String technology) {
        return projectRepo.findAllByTechnology(technologyService.getTechnologyByName(technology.trim()));
    }

    public List<Project> getAllProjectsForCurrentUser(Principal principal) {
        return projectRepo.findAllByOwnerOrderByCreatedDate(userService.getUserByPrincipal(principal));
    }

    public LinkedHashSet<Project> getAllProjectsWithPosition(String nameOfPosition) {
        List<Participant> parts = participantRepo.findAllByNameOfPositionIgnoreCase(nameOfPosition.trim());
        LinkedHashSet<Project> projects = new LinkedHashSet<>();
        parts.forEach(part -> projects.add(part.getProject()));

        return projects;
    }

    public Project applyOnProject(Long projectId, String nameOfPosition, Principal principal) {
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

    public Project confirmParticipant(Long projectId, String nameOfPosition, Long participantId, Principal principal) {
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

    public Project rejectParticipant(Long projectId, String nameOfPosition, Long participantId, Principal principal) {
        Project project = getProjectById(projectId);

        if (!projectIsBelongUser(project, principal))
            throw  new RuntimeException("This project cannot be changed");

        User user = userService.getUserById(participantId);
        project.getParticipants().forEach(participant -> {
            if (participant.getNameOfPosition().equals(nameOfPosition)) {
                participant.getRequests().remove(user);
            }
        });
        projectRepo.save(project);
        return projectRepo.save(project);
    }

    private Project projectRequestToProject(ProjectRequest projectRequest, Principal principal) {
        Project project = new Project();

        project.setTitle(projectRequest.getTitle());
        project.setDescription(projectRequest.getDescription());
        project.setTechnology(technologyService.convertTechnology(projectRequest.getTechnology()));
        project.setCommercialStatus(project.getCommercialStatus());
//        project.setCoverLink(projectRequest.getCover().getName());
        project.setOwner(userService.getUserByPrincipal(principal));
        return project;
    }

    public boolean projectIsBelongUser(Project project, Principal principal) {
        return project.getOwner().equals(userService.getUserByPrincipal(principal));
    }
}
