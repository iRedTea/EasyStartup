package site.easystartup.easystartupcore.project.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.easystartup.easystartupcore.project.domain.model.Project;

@Repository
public interface ProjectRepo extends JpaRepository<Project, Long> {
}
