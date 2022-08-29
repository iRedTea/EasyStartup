package site.easystartup.easystartupcore.forum.domain;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Discussion implements Comparable<Discussion>{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private String author;
    private Date date;
    private Date last_update;
    private boolean pinned;
    private DiscussionStatus status;
    private long topic;

    @ElementCollection(targetClass = Long.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "discussion_messages", joinColumns = @JoinColumn(name = "discussion_id"))
    private List<Long> messages;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Discussion that = (Discussion) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public int compareTo(Discussion o) {
        if (getLast_update() == null || o.getLast_update() == null)
            return 0;
        return getLast_update().compareTo(o.getLast_update());
    }
}
