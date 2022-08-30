package site.easystartup.web.project.domain.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long tagId;

    @Column(name = "tage_name")
    private String tagName;

    @ManyToMany(mappedBy = "technology")
    private Set<Project> projects;
}
