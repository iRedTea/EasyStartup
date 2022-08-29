package site.easystartup.easystartupcore.repos.forum;

import org.springframework.data.repository.CrudRepository;
import site.easystartup.easystartupcore.domain.forum.Discussion;

public interface DiscussionRepo extends CrudRepository<Discussion, Long> {
}
