package site.easystartup.web.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import site.easystartup.web.domain.model.User;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);

    Optional<User> findUserByUsername(String username);
}
