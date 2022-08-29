package site.easystartup.easystartupcore.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import site.easystartup.easystartupcore.domain.User;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
