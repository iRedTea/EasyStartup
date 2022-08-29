package site.easystartup.easystartupcore.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.easystartup.easystartupcore.project.repo.ProjectRepo;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepo projectRepo;




}
