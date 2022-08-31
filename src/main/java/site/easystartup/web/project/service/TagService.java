package site.easystartup.web.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.easystartup.web.project.domain.model.Tag;
import site.easystartup.web.project.repo.TagRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepo tagRepo;

    public List<Tag> getAllTags() {
        return tagRepo.findAll();
    }
    public List<Tag> convertTechnology(List<Tag> technology) {
        List<Tag> tags = new ArrayList<>();
        technology.forEach(tag -> {
            Optional<Tag> foundTag = tagRepo.findTagByTagNameIgnoreCase(tag.getTagName());
            if (foundTag.isPresent()) {
                tags.add(foundTag.get());
            }
            else {
                tagRepo.save(tag);
                tags.add(tagRepo.findTagByTagNameIgnoreCase(tag.getTagName()).get());
            }
        });
        return tags;
    }

    public Tag getTechnologyByName(String tag) {
        return tagRepo.findTagByTagNameIgnoreCase(tag).orElseThrow(() -> new RuntimeException("Technolohy not found"));
    }
}
