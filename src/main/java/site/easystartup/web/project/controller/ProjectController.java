package site.easystartup.web.project.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import site.easystartup.web.project.domain.model.Project;
import site.easystartup.web.project.domain.requst.ParticipantRequest;
import site.easystartup.web.project.domain.requst.ProjectRequest;
import site.easystartup.web.domain.response.MessageResponse;
import site.easystartup.web.domain.validation.ResponseErrorValidation;
import site.easystartup.web.project.dto.ParticipantDto;
import site.easystartup.web.project.dto.ProjectDto;
import site.easystartup.web.project.service.ProjectService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project")
public class ProjectController {
    private final ProjectService projectService;
    private final ResponseErrorValidation responseErrorValidation;
    private final ModelMapper modelMapper;

    @PostMapping("/")
    public ResponseEntity<Object> createProject(@Valid @RequestBody ProjectRequest projectRequest,
                                                BindingResult bindingResult,
                                                Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        Project project = projectService.createProject(projectRequest, principal);
        return ResponseEntity.ok().body(modelMapper.map(project, ProjectDto.class));
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<Object> editProject(@Valid @RequestBody ProjectRequest projectRequest,
                                              @PathVariable("projectId") Long projectId,
                                              BindingResult bindingResult,
                                              Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        Project projectUpdated = projectService.editProject(projectRequest, projectId, principal);
        return ResponseEntity.ok().body(modelMapper.map(projectUpdated, ProjectDto.class));
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<Object> deleteProject(@PathVariable("projectId") Long projectId,
                                                Principal principal) {
        projectService.deleteProject(projectId, principal);
        return ResponseEntity.ok(new MessageResponse("Project was deleted!"));
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectDto> getProject(@PathVariable("projectId") Long projectId) {
        return ResponseEntity.ok().body(modelMapper.map(projectService.getProjectById(projectId), ProjectDto.class));
    }

    //
    @GetMapping("/{userId}")
    public ResponseEntity<List<ProjectDto>> getAllProjectsForUser(@PathVariable("userId") Long userId) {
        List<ProjectDto> projects = projectService.getAllProjectsForUser(userId)
                .stream().map(project -> modelMapper.map(project, ProjectDto.class)).collect(Collectors.toList());
        return ResponseEntity.ok().body(projects);
    }

    @GetMapping("/{technology}")
    public ResponseEntity<List<ProjectDto>> getAllProjectsWithTechnology(@PathVariable("technology") String technology) {
        List<ProjectDto> projects = projectService.getAllProjectsWithTechnology(technology)
                .stream().map(project -> modelMapper.map(project, ProjectDto.class)).collect(Collectors.toList());
        return ResponseEntity.ok().body(projects);
    }

    @GetMapping("/my")
    public ResponseEntity<List<ProjectDto>> getAllProjectsForCurrentUser(Principal principal) {
        List<ProjectDto> projects = projectService.getAllProjectsForCurrentUser(principal)
                .stream().map(project -> modelMapper.map(project, ProjectDto.class)).collect(Collectors.toList());
        return ResponseEntity.ok().body(projects);
    }


    @GetMapping("/positions")
    public ResponseEntity<List<ProjectDto>> getAllProjectsWithPosition(@RequestBody String nameOfPosition) {
        List<ProjectDto> projects = projectService.getAllProjectsWithPosition(nameOfPosition)
                .stream().map(project -> modelMapper.map(project, ProjectDto.class)).collect(Collectors.toList());
        return ResponseEntity.ok().body(projects);
    }

    @GetMapping("/commercial_status")
    public ResponseEntity<List<ProjectDto>> getAllProjectsWithCommercialStatus(@RequestBody int commercialStatus) {
        List<ProjectDto> projects = projectService.getAllProjectsWithCommercialStatus(commercialStatus)
                .stream().map(project -> modelMapper.map(project, ProjectDto.class)).collect(Collectors.toList());
        return ResponseEntity.ok().body(projects);
    }

    @GetMapping("/requests")
    public ResponseEntity<List<ParticipantDto>> getAllRequestsForProject(@RequestBody Long projectId) {
        List<ParticipantDto> requests = projectService.getAllRequestsForProject(projectId)
                .stream().map(project -> modelMapper.map(project, ParticipantDto.class)).collect(Collectors.toList());
        return ResponseEntity.ok().body(requests);
    }

    @PostMapping("/apply")
    public ResponseEntity<ProjectDto> applyOnProject(@RequestBody ParticipantRequest participantRequest,
                                                     Principal principal) {
        Project project = projectService
                .applyOnProject(participantRequest.getProjectId(), participantRequest.getNameOfPosition(), principal);
        return ResponseEntity.ok().body(modelMapper.map(project, ProjectDto.class));
    }

    @PostMapping("/participant")
    public ResponseEntity<ProjectDto> confirmParticipant(@RequestBody ParticipantRequest participantRequest,
                                                         Principal principal) {
        Project project = projectService.confirmParticipant(participantRequest.getProjectId(),
                participantRequest.getNameOfPosition(),
                participantRequest.getParticipantId(),
                principal);

        return ResponseEntity.ok().body(modelMapper.map(project, ProjectDto.class));
    }
}
