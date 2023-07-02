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
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long tagId;

    @Column(name = "tage_name")
    private String tagName;

    @ManyToMany(mappedBy = "projects")
    @ToString.Exclude
    private Set<Project> projects;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Tag tag = (Tag) o;
        return tagId != null && Objects.equals(tagId, tag.tagId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
