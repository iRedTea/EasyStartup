package site.easystartup.web.project.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import site.easystartup.web.domain.model.User;
import site.easystartup.web.project.domain.model.Project;
import site.easystartup.web.project.domain.model.Tag;

import java.util.List;

@Repository
public interface ProjectRepo extends JpaRepository<Project, Long> {

    List<Project> findAllByOwnerOrderByCreatedDate(User owner);
    List<Project> findAllByTechnology(Tag tag);

    List<Project> findAllByCommercialStatusOrderByCreatedDateAsc(int commercialStatus);

}
