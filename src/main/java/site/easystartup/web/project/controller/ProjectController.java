package site.easystartup.web.project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import site.easystartup.web.project.service.ParticipantService;
import site.easystartup.web.project.service.ProjectService;
import site.easystartup.web.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/project")
@Tag(name = "Проекты", description = "контроллер для работы с проектами")
public class ProjectController {
    private final ProjectService projectService;
    private final ResponseErrorValidation responseErrorValidation;
    private final ModelMapper modelMapper;

    private final ParticipantService participantService;

    private final UserService userService;

    @PostMapping
    @Operation(summary = "Создание проекта")
    public ResponseEntity<Object> createProject(@Valid @RequestBody ProjectRequest projectRequest,
                                                BindingResult bindingResult,
                                                Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        Project project = projectService.createProject(projectRequest, principal);
        return ResponseEntity.ok().body(modelMapper.map(project, ProjectDto.class));
    }

    @PutMapping("/{projectId}")
    @Operation(summary = "Изменение проекта")
    public ResponseEntity<Object> editProject(@Valid @RequestBody ProjectRequest projectRequest,
                                              @PathVariable("projectId") Long projectId,
                                              BindingResult bindingResult) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        Project projectUpdated = projectService.editProject(projectRequest, projectId);
        return ResponseEntity.ok().body(modelMapper.map(projectUpdated, ProjectDto.class));
    }

    @PutMapping("/{projectId}/{partId}/part")
    @Operation(summary = "Изменение позиции в проекте: Дизайнер/Ui designer")
    public ResponseEntity<Object> editPart(@Valid @RequestBody ParticipantDto participantDto,
                                           @PathVariable("projectId") Long projectId,
                                           @PathVariable("partId") Long partId,
                                           BindingResult bindingResult) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        Project projectUpdated = participantService.edit(participantDto, partId, projectId,  userService.getCurrentUsername());
        return ResponseEntity.ok().body(modelMapper.map(projectUpdated, ProjectDto.class));
    }


    @DeleteMapping("/{projectId}")
    @Operation(summary = "Удаление проекта")
    public ResponseEntity<Object> deleteProject(@PathVariable("projectId") Long projectId) {
        projectService.deleteProject(projectId);
        return ResponseEntity.ok(new MessageResponse("Project was deleted!"));
    }

    @GetMapping("/{projectId}")
    @Operation(summary = "получение проекта по id")
    public ResponseEntity<ProjectDto> getProject(@PathVariable("projectId") Long projectId) {
        return ResponseEntity.ok().body(modelMapper.map(projectService.getProjectById(projectId), ProjectDto.class));
    }

    //
    @GetMapping("/{userId}/all")
    @Operation(summary = "Получение всех проектов для пользователя")
    public ResponseEntity<List<ProjectDto>> getAllProjectsForUser(@PathVariable("userId") Long userId) {
        List<ProjectDto> projects = projectService.getAllProjectsForUser(userId)
                .stream().map(project -> modelMapper.map(project, ProjectDto.class)).collect(Collectors.toList());
        return ResponseEntity.ok().body(projects);
    }

    @GetMapping("/{technology}/tech")
    @Operation(summary = "Получение всех проектов с опредлененной технологией")
    public ResponseEntity<List<ProjectDto>> getAllProjectsWithTechnology(@PathVariable("technology") String technology) {
        List<ProjectDto> projects = projectService.getAllProjectsWithTechnology(technology)
                .stream().map(project -> modelMapper.map(project, ProjectDto.class)).collect(Collectors.toList());
        return ResponseEntity.ok().body(projects);
    }

    @GetMapping("/my")
    @Operation(summary = "получение всех проектов для текущего пользователя")
    public ResponseEntity<List<ProjectDto>> getAllProjectsForCurrentUser() {
        List<ProjectDto> projects = projectService.getAllProjectsForCurrentUser()
                .stream().map(project -> modelMapper.map(project, ProjectDto.class)).collect(Collectors.toList());
        return ResponseEntity.ok().body(projects);
    }


    @GetMapping("/{nameOfPosition}/positions")
    @Operation(summary = "Получение всех проектов с указанной позицией : Дизайнер")
    public ResponseEntity<List<ProjectDto>> getAllProjectsWithPosition(@PathVariable("nameOfPosition") String nameOfPosition) {
        List<ProjectDto> projects = projectService.getAllProjectsWithPosition(nameOfPosition)
                .stream().map(project -> modelMapper.map(project, ProjectDto.class)).collect(Collectors.toList());
        return ResponseEntity.ok().body(projects);
    }

    @GetMapping("/{commercialStatus}/commercial_status")
    @Operation(summary = "получение всех проектов по коммерческому статусу")
    public ResponseEntity<List<ProjectDto>> getAllProjectsWithCommercialStatus(@PathVariable("commercialStatus") int commercialStatus) {
        List<ProjectDto> projects = projectService.getAllProjectsWithCommercialStatus(commercialStatus)
                .stream().map(project -> modelMapper.map(project, ProjectDto.class)).collect(Collectors.toList());
        return ResponseEntity.ok().body(projects);
    }

    @GetMapping("/requests")
    @Operation(summary = "получение всех поданных заявок на проект")
    public ResponseEntity<List<ParticipantDto>> getAllRequestsForProject(@RequestBody Long projectId) {
        List<ParticipantDto> requests = projectService.getAllRequestsForProject(projectId)
                .stream().map(project -> modelMapper.map(project, ParticipantDto.class)).collect(Collectors.toList());
        return ResponseEntity.ok().body(requests);
    }

    @PostMapping("/apply")
    @Operation(summary = "подать заявку на участие в проекте")
    public ResponseEntity<ProjectDto> applyOnProject(@RequestBody ParticipantRequest participantRequest,
                                                     Principal principal) {
        Project project = projectService
                .applyOnProject(participantRequest.getProjectId(), participantRequest.getNameOfPosition());
        return ResponseEntity.ok().body(modelMapper.map(project, ProjectDto.class));
    }

    @PostMapping("/participant")
    @Operation(summary = "утвердить участница на проект")
    public ResponseEntity<ProjectDto> confirmParticipant(@RequestBody ParticipantRequest participantRequest) {
        Project project = projectService.confirmParticipant(participantRequest.getProjectId(),
                participantRequest.getNameOfPosition(),
                participantRequest.getParticipantId(),
                userService.getCurrentUsername());

        return ResponseEntity.ok().body(modelMapper.map(project, ProjectDto.class));
    }
}
