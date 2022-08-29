package site.easystartup.easystartupcore.repos.forum;

import org.springframework.data.repository.CrudRepository;
import site.easystartup.easystartupcore.domain.forum.Discussion;
import site.easystartup.easystartupcore.domain.forum.DiscussionMessage;

public interface DiscussionMessageRepo extends CrudRepository<DiscussionMessage, Long> {
}
