package site.easystartup.web.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import site.easystartup.web.domain.model.User;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
