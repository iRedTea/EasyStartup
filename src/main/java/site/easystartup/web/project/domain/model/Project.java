package site.easystartup.web.project.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import site.easystartup.web.domain.model.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "projectId")
    private Long projectId;

    @Column(name = "coverLink")
    private String coverLink;

    @Column(name = "title")
    @Size(min = 3, max = 100, message = "Title should be no less 3 and no more 100 signs")
    @NotNull
    private String title;

    @Column(name = "description")
    @Size(min = 50, message = "Description should be no less 50 signs")
    @NotNull
    private String description;

    @Column(name = "commercialStatus") // 1 - commercial, 0 - non-commercial
    private int commercialStatus;

    @ManyToMany
    @JoinTable(name = "projectTag",
            joinColumns = @JoinColumn(name = "projectId"),
            inverseJoinColumns = @JoinColumn(name = "technologyId")
    )
    private List<Technology> technology;

    @ManyToOne
    @JoinColumn(name = "ownerId", updatable = false)
    private User owner;

    @OneToMany(mappedBy = "project", cascade = {CascadeType.ALL})
//    @ToString.Exclude
    private List<Participant> participants;

    @Column(name = "createdDate", updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    @PrePersist
    private void createdDate() {
        this.createdDate = LocalDateTime.now();
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
//        Project project = (Project) o;
//        return projectId != null && Objects.equals(projectId, project.projectId);
//    }
//
//    @Override
//    public int hashCode() {
//        return getClass().hashCode();
//    }
}
