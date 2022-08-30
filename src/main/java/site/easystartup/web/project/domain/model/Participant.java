package site.easystartup.web.project.domain.model;

import lombok.Data;
import site.easystartup.web.domain.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "participant")
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participant_id")
    private Long participantId;

    @ManyToOne
    @JoinColumn(name = "project_id", updatable = false)
    private Project project;

    @Column(name = "technology")
    @NotNull
    private String technology;

    @Column(name = "name_of_position")
    private String nameOfPosition;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(name = "user_request",
    joinColumns = @JoinColumn(name = "participant_id"),
    inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> requests;
}
