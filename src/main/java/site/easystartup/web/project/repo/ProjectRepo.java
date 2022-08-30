package site.easystartup.web.project.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import site.easystartup.web.domain.User;
import site.easystartup.web.project.domain.model.Project;

import java.util.List;
import java.util.Set;

@Repository
public interface ProjectRepo extends JpaRepository<Project, Long> {

    List<Project> findAllByOwnerOrderByCreatedDate(User owner);

    @Query("SELECT p from Project p WHERE p.technology LIKE %:technology%")
    List<Project> findAllByTechnology(@Param("technology") String tech);

    List<Project> findAllByCommercialStatusOrderByCreatedDateAsc(int commercialStatus);

}
