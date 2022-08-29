package site.easystartup.easystartupcore.forum.repo;

import org.springframework.data.repository.CrudRepository;
import site.easystartup.easystartupcore.forum.domain.Topic;

public interface TopicRepo extends CrudRepository<Topic, Long> {
}
