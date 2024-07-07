package site.easystartup.web.project.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.easystartup.web.domain.model.User;
import site.easystartup.web.project.domain.model.Project;
import site.easystartup.web.project.domain.model.Technology;

import java.util.List;

@Repository
public interface ProjectRepo extends JpaRepository<Project, Long> {

    List<Project> findAllByOwnerOrderByCreatedDate(User owner);
    List<Project> findAllByTechnology(Technology technology);

    List<Project> findAllByCommercialStatusOrderByCreatedDateAsc(int commercialStatus);

}
