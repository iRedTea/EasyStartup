package site.easystartup.easystartupcore.poject.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.easystartup.easystartupcore.poject.domain.model.Project;

@Repository
public interface ProjectRepo extends JpaRepository<Project, Long> {
}
