package site.easystartup.easystartupcore.project.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import site.easystartup.easystartupcore.project.domain.model.Project;
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

//    @PostMapping("/create")
//    public ModelAndView createProject(@ModelAttribute("project") ProjectDto projectDto,
//                                      BindingResult bindingResult,
//                                      Principal principal) {
//        ModelAndView modelAndView = new ModelAndView();
//
//        if (bindingResult.hasErrors()) {
//            Map<String, String> errors = responseErrorValidation.mapValidationService(bindingResult);
//            return modelAndView.addObject("errors", errors);
//        }
//
//        Project project = projectService.createProject(modelMapper.map(projectDto, Project.class), principal);
//        modelAndView.addObject("project", project);
//        modelAndView.setViewName("project");
//        return modelAndView;
//    }
//
//    @PatchMapping("/{projectId}/update")
//    public ModelAndView updateProject() {
//        ModelAndView modelAndView = new ModelAndView();
//
//        return modelAndView;
//    }
//
//    @DeleteMapping("/{projectId}/delete")
//    public ModelAndView deleteProject() {
//        ModelAndView modelAndView = new ModelAndView();
//
//        return modelAndView;
//    }
//
//    @GetMapping("/{projectId}")
//    public ModelAndView getProject() {
//        ModelAndView modelAndView = new ModelAndView();
//
//        return modelAndView;
//    }
//
//    @GetMapping("/{userId}")
//    public ModelAndView getAllProjectsForUser() {
//    }
//
//    @GetMapping("/{technology}")
//    public ModelAndView getAllProjectsWithTechnology() {
//    }
//
//    @GetMapping("/my")
//    public ModelAndView getAllProjectsForCurrentUser() {
//    }
//
//    @PostMapping("/{projectId}/applay")
//    public ModelAndView applayOnProject() {
//        ModelAndView modelAndView = new ModelAndView();
//
//        return modelAndView;
//    }
//
//    @PostMapping("/{projectId}/{participantId}")
//    public ModelAndView confirmParticipant() {
//        ModelAndView modelAndView = new ModelAndView();
//
//        return modelAndView;
//    }


}
