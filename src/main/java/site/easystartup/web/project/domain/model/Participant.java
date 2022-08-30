package site.easystartup.web.project.domain.model;

import lombok.*;
import org.hibernate.Hibernate;
import site.easystartup.web.domain.User;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
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

    @Column(name = "name_of_position")
    private String nameOfPosition;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(name = "user_request",
    joinColumns = @JoinColumn(name = "participant_id"),
    inverseJoinColumns = @JoinColumn(name = "user_id"))
    @ToString.Exclude
    private List<User> requests;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Participant that = (Participant) o;
        return participantId != null && Objects.equals(participantId, that.participantId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
