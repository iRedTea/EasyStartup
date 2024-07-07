package site.easystartup.web.project.domain.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "tag")
public class Technology {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "technologyId")
    private Long technologyId;

    @Column(name = "technologyName")
    private String technologyName;

    @ManyToMany(mappedBy = "technology")
    private Set<Project> projects;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Technology technology = (Technology) o;
        return technologyId != null && Objects.equals(technologyId, technology.technologyId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
