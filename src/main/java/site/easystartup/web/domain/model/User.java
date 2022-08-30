package site.easystartup.web.domain.model;

import lombok.*;
import org.hibernate.Hibernate;
import site.easystartup.web.project.domain.model.Participant;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "usr")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private long id;
    private String username;
    private String password;
    private boolean active;
    private String full_name;
    private String status;
    private String iconPath;
    private String email;

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_tag", joinColumns = @JoinColumn(name = "user_id"))
    private Set<String> tags;

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_profession", joinColumns = @JoinColumn(name = "user_id"))
    private Set<String> professions;

    @ElementCollection(targetClass = Long.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_projects", joinColumns = @JoinColumn(name = "user_id"))
    private Set<Long> projects;

    @ManyToMany(mappedBy = "requests")
    private List<Participant> requests;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public boolean isModer() {
        if (roles.contains(Role.MODER) || roles.contains(Role.ADMIN)) return true;
        return false;
    }

    public boolean isAdmin() {
        if (roles.contains(Role.ADMIN)) return true;
        return false;
    }

    public boolean isPremium() {
        if (roles.contains(Role.PREMIUM) || roles.contains(Role.ADMIN)) return true;
        return false;
    }
}
