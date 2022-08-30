package site.easystartup.web.forum.repo;

import org.springframework.data.repository.CrudRepository;
import site.easystartup.web.forum.domain.model.DiscussionMessage;

public interface DiscussionMessageRepo extends CrudRepository<DiscussionMessage, Long> {
}
