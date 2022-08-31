package site.easystartup.web.forum.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import site.easystartup.web.forum.domain.model.Discussion;

public interface DiscussionRepo extends JpaRepository<Discussion, Long> {
}
