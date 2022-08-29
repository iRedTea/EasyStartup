package site.easystartup.web.project.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import site.easystartup.web.project.domain.model.Project;
import site.easystartup.web.project.domain.payload.requst.ProjectRequest;
import site.easystartup.web.project.domain.validation.ResponseErrorValidation;
import site.easystartup.web.project.dto.ProjectDto;
import site.easystartup.web.project.service.ProjectService;

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
    public ModelAndView createProject(@ModelAttribute("project") ProjectRequest projectRequest,
                                      BindingResult bindingResult,
                                      Principal principal) {
        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = responseErrorValidation.mapValidationService(bindingResult);
            return modelAndView.addObject("errors", errors);
        }

        Project project = projectService.createProject(projectRequest, principal);
        modelAndView.addObject("project", project);
        modelAndView.setViewName("project");
        return modelAndView;
    }

    @PatchMapping("/{projectId}/edit")
    public ModelAndView editProject(@ModelAttribute("projectUpdate") ProjectRequest projectRequest,
                                      @PathVariable("projectId") Long projectId,
                                      BindingResult bindingResult,
                                      Principal principal) {
        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = responseErrorValidation.mapValidationService(bindingResult);
            return modelAndView.addObject("errors", errors);
        }

        Project projectUpdated = projectService
                .editProject(projectRequest, projectId, principal);
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

    @GetMapping("/{commercialStatus}")
    public ModelAndView getAllProjectsWithCommercialStatus(@PathVariable("commercialStatus") int commercialStatus) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("projects", projectService.getAllProjectsWithCommercialStatus(commercialStatus));
        modelAndView.setViewName("all-projects");


        return modelAndView;
    }

    @PostMapping("/{projectId}/{nameOfPosition}/applay")
    public ModelAndView applayOnProject(@PathVariable("projectId") Long projectId,
                                        @PathVariable("nameOfPosition") String nameOfPosition,
                                        Principal principal) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("message", projectService.applayOnProject(projectId, nameOfPosition, principal));
        modelAndView.addObject("project", modelMapper.map(projectService.getProjectById(projectId), ProjectDto.class));
        modelAndView.setViewName("project");

        return modelAndView;
    }

    @PostMapping("/{projectId}/{participantId}/{nameOfPosition}")
    public ModelAndView confirmParticipant(@PathVariable("projectId") Long projectId,
                                           @PathVariable("participantId") Long participantId,
                                           @PathVariable("nameOfPosition") String nameOfPosition,
                                           Principal principal) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("message", projectService.confirmParticipant(projectId, nameOfPosition, participantId, principal));
        modelAndView.addObject("project", projectService.getProjectById(projectId));
        modelAndView.setViewName("project");

        return modelAndView;
    }


}
