package site.easystartup.web.project.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import site.easystartup.web.project.domain.model.Participant;
import site.easystartup.web.project.domain.model.Project;

import java.util.LinkedHashSet;
import java.util.List;

@Repository
public interface ParticipantRepo extends JpaRepository<Participant, Long> {

    List<Participant> findAllByNameOfPositionIgnoreCase(String name);

}
