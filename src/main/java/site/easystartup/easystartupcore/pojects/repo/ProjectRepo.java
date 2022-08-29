package site.easystartup.easystartupcore.pojects.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.easystartup.easystartupcore.pojects.domain.model.Project;

@Repository
public interface ProjectRepo extends JpaRepository<Project, Long> {
}
