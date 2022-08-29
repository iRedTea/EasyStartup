package site.easystartup.easystartupcore.forum.repo;

import org.springframework.data.repository.CrudRepository;
import site.easystartup.easystartupcore.forum.domain.Discussion;

public interface DiscussionRepo extends CrudRepository<Discussion, Long> {
}
