package site.easystartup.web.post.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import site.easystartup.web.domain.model.Role;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "pst")
public class Post implements Comparable<Post>{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_id")
    private long id;
    @Column(columnDefinition="varchar(1024)")
    private String text;
    private String sender;
    @Column(name = "lk")
    private long like;

    @ElementCollection(targetClass = Long.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "post_answers", joinColumns = @JoinColumn(name = "post_id"))
    private Set<Long> answers;

    @ElementCollection(targetClass = String.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "post_liked", joinColumns = @JoinColumn(name = "post_id"))
    private Set<String> liked_users;

    private Long answered_post;

    @Column(name = "date", updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    private boolean edited;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Post that = (Post) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public int compareTo(Post o) {
        if (getDate() == null || o.getDate() == null)
            return 0;
        return getDate().compareTo(o.getDate());
    }

}