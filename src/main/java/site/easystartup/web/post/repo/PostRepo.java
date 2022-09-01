package site.easystartup.web.post.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import site.easystartup.web.post.domain.model.Post;

public interface PostRepo extends JpaRepository<Post, Long> {
}
