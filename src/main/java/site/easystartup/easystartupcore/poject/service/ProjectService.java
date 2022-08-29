package site.easystartup.easystartupcore.poject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.easystartup.easystartupcore.poject.repo.ProjectRepo;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepo projectRepo;




}
