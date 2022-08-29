package site.easystartup.easystartupcore.repos.forum;

import org.springframework.data.repository.CrudRepository;
import site.easystartup.easystartupcore.domain.forum.Discussion;
import site.easystartup.easystartupcore.domain.forum.Topic;

public interface TopicRepo extends CrudRepository<Topic, Long> {
}
