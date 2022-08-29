package site.easystartup.easystartupcore.forum.repo;

import org.springframework.data.repository.CrudRepository;
import site.easystartup.easystartupcore.forum.domain.DiscussionMessage;

public interface DiscussionMessageRepo extends CrudRepository<DiscussionMessage, Long> {
}
