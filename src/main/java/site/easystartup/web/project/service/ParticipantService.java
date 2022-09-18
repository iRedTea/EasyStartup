package site.easystartup.web.project.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import site.easystartup.web.project.domain.model.Participant;
import site.easystartup.web.project.domain.model.Project;
import site.easystartup.web.project.domain.requst.ProjectRequest;
import site.easystartup.web.project.dto.ParticipantDto;
import site.easystartup.web.project.repo.ParticipantRepo;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class ParticipantService {
    private final ParticipantRepo participantRepo;
    private final ModelMapper modelMapper;
    private final ProjectService projectService;

    public Project edit(ParticipantDto participantDto,
                        Long partId,
                        Long projectId, String username) {
        Project project = projectService.getProjectById(projectId);
        if (!projectService.projectIsBelongUser(project, username))
            throw  new RuntimeException("This project cannot be changed");
        Participant participantOld = participantRepo.findById(partId).orElseThrow(() -> new RuntimeException());
        if (!partIsBelongProject(project, participantOld))
            throw  new RuntimeException("This participant cannot be changed");

        Participant participant = modelMapper.map(participantDto, Participant.class);
        participant.setParticipantId(partId);
        participantRepo.save(participant);
        return projectService.getProjectById(projectId);
    }

    private boolean partIsBelongProject(Project project, Participant participant) {
        return project.equals(participant.getProject());
    }


}
