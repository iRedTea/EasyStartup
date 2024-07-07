package site.easystartup.web.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.easystartup.web.project.domain.model.Technology;
import site.easystartup.web.project.repo.TechnologyRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TechnologyService {
    private final TechnologyRepo technologyRepo;

    public List<Technology> getAllTags() {
        return technologyRepo.findAll();
    }
    public List<Technology> convertTechnology(List<Technology> technology) {
        List<Technology> technologies = new ArrayList<>();
        technology.forEach(tag -> {
            Optional<Technology> foundTag = technologyRepo.findTechnologyByTechnologyName(tag.getTechnologyName());
            if (foundTag.isPresent()) {
                technologies.add(foundTag.get());
            }
            else {
                technologyRepo.save(tag);
                technologies.add(technologyRepo.findTechnologyByTechnologyName(tag.getTechnologyName()).get());
            }
        });
        return technologies;
    }

    public Technology getTechnologyByName(String tag) {
        return technologyRepo.findTechnologyByTechnologyName(tag).orElseThrow(() -> new RuntimeException("Technolohy not found"));
    }
}
