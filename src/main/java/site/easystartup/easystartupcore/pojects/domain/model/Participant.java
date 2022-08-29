package site.easystartup.easystartupcore.pojects.domain.model;

import lombok.Data;
import site.easystartup.easystartupcore.domain.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(name = "technology")
    @NotNull
    private String technology;

    @Column(name = "name_of_position")
    private String nameOfPosition;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @OneToMany
    private Set<User> requests;
}
