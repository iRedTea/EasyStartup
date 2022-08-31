package site.easystartup.web.project.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import site.easystartup.web.project.domain.model.Participant;
import site.easystartup.web.project.domain.model.Project;

import java.util.LinkedHashSet;

@Repository
public interface ParticipantRepo extends JpaRepository<Participant, Long> {
    @Query ("SELECT p FROM Participant p WHERE p.nameOfPosition = :nameOfPosition")
    LinkedHashSet<Project> findAllByNameOfPosition(@Param("nameOfPosition") String name);
}
