package site.easystartup.web.project.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.easystartup.web.project.domain.model.Tag;

import java.util.Optional;

@Repository
public interface TagRepo extends JpaRepository<Tag, Long> {

    Optional<Tag> findTagByTagNameIgnoreCase(String tagName);
}
