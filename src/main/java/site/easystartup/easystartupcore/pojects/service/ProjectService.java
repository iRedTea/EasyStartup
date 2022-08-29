package site.easystartup.easystartupcore.pojects.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.easystartup.easystartupcore.pojects.repo.ProjectRepo;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepo projectRepo;




}
