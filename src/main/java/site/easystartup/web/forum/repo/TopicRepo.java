package site.easystartup.web.forum.repo;

import org.springframework.data.repository.CrudRepository;
import site.easystartup.web.forum.domain.Topic;

public interface TopicRepo extends CrudRepository<Topic, Long> {
}
