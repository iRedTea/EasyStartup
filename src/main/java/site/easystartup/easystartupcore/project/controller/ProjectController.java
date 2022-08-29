package site.easystartup.easystartupcore.project.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import site.easystartup.easystartupcore.project.domain.model.Project;
import site.easystartup.easystartupcore.project.domain.payload.response.ResponseMessage;
import site.easystartup.easystartupcore.project.domain.validation.ResponseErrorValidation;
import site.easystartup.easystartupcore.project.dto.ProjectDto;
import site.easystartup.easystartupcore.project.service.ProjectService;

import java.security.Principal;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/project")
public class ProjectController {
    private final ProjectService projectService;
    private final ResponseErrorValidation responseErrorValidation;
    private final ModelMapper modelMapper;

    @PostMapping("/create")
    public ModelAndView createProject(@ModelAttribute("project") ProjectDto projectDto,
                                      BindingResult bindingResult,
                                      Principal principal) {
        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = responseErrorValidation.mapValidationService(bindingResult);
            return modelAndView.addObject("errors", errors);
        }

        Project project = projectService.createProject(modelMapper.map(projectDto, Project.class), principal);
        modelAndView.addObject("project", project);
        modelAndView.setViewName("project");
        return modelAndView;
    }

    @PatchMapping("/{projectId}/update")
    public ModelAndView updateProject(@ModelAttribute("projectUpdate") ProjectDto projectDto,
                                      @PathVariable("projectId") Long projectId,
                                      BindingResult bindingResult,
                                      Principal principal) {
        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = responseErrorValidation.mapValidationService(bindingResult);
            return modelAndView.addObject("errors", errors);
        }

        Project projectUpdated = projectService
                .updateProject(modelMapper.map(projectDto, Project.class), projectId, principal);
        modelAndView.addObject(projectUpdated);
        modelAndView.setViewName("project");
        return modelAndView;
    }

    @DeleteMapping("/{projectId}/delete")
    public ModelAndView deleteProject(@PathVariable("projectId") Long projectId,
                                      Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        projectService.deleteProject(projectId, principal);
        modelAndView.setViewName("redirect:/my");                                                           // some ?
        return modelAndView;
    }

    @GetMapping("/{projectId}")
    public ModelAndView getProject(@PathVariable("projectId") Long projectId) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("project", projectService.getProjectById(projectId));
        modelAndView.setViewName("project");

        return modelAndView;
    }

    @GetMapping("/{userId}")
    public ModelAndView getAllProjectsForUser(@PathVariable("userId") Long userId) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("projects", projectService.getAllProjectsForUser(userId));
        modelAndView.setViewName("all-projects");
        return modelAndView;
    }

    @GetMapping("/{technology}")
    public ModelAndView getAllProjectsWithTechnology(@PathVariable("technology") String technology) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("projects", projectService.getAllProjectsWithTechnology(technology));
        modelAndView.setViewName("all-projects");
        return modelAndView;
    }

    @GetMapping("/my")
    public ModelAndView getAllProjectsForCurrentUser(Principal principal) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject(projectService.getAllProjectsForCurrentUser(principal));
        modelAndView.setViewName("all-projects");
        return modelAndView;
    }


    @GetMapping("/{nameOfPosition}")
    public ModelAndView getAllProjectsWithPosition(@PathVariable("nameOfPosition") String nameOfPosition) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("projects", projectService.getAllProjectsWithPosition(nameOfPosition));
        modelAndView.setViewName("all-projects");


        return modelAndView;
    }

    @PostMapping("/{projectId}/applay")
    public ModelAndView applayOnProject(@PathVariable("projectId") Long projectId,
                                        Principal principal) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("message", projectService.applayOnProject(projectId, principal));
        modelAndView.addObject("project", modelMapper.map(projectService.getProjectById(projectId), ProjectDto.class));
        modelAndView.setViewName("project");

        return modelAndView;
    }

    @PostMapping("/{projectId}/{participantId}")
    public ModelAndView confirmParticipant(@PathVariable("projectId") Long projectId,
                                           @PathVariable("participantId") Long participantId,
                                           Principal principal) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("message", projectService.confirmParticipant(projectId, participantId, principal));
        modelAndView.addObject("project", projectService.getProjectById(projectId));
        modelAndView.setViewName("project");

        return modelAndView;
    }


}
