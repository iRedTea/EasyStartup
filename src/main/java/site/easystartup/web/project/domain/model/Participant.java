package site.easystartup.web.project.domain.model;

import lombok.*;
import org.hibernate.Hibernate;
import site.easystartup.web.domain.model.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "participant")
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participantId")
    private Long participantId;

    @ManyToOne
    @JoinColumn(name = "projectId", updatable = false)
    private Project project;

    @Column(name = "nameOfPosition")
    @NotNull
    private String nameOfPosition;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToMany
    @JoinTable(name = "userRequest",
    joinColumns = @JoinColumn(name = "participantId"),
    inverseJoinColumns = @JoinColumn(name = "userId"))
    @ToString.Exclude
    private List<User> requests;

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
//        Participant that = (Participant) o;
//        return participantId != null && Objects.equals(participantId, that.participantId);
//    }

//    @Override
//    public int hashCode() {
//        return getClass().hashCode();
//    }
}
