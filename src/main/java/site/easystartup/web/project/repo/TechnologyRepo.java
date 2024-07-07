package site.easystartup.web.project.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.easystartup.web.project.domain.model.Technology;

import java.util.Optional;

@Repository
public interface TechnologyRepo extends JpaRepository<Technology, Long> {

    Optional<Technology> findTechnologyByTechnologyName(String technologyName);
}
