package site.easystartup.web.chat.domain.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class ChatRoom implements Comparable<ChatRoom> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String chatId;
    private String sender;
    private String recipient;
    private Date last_update;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ChatRoom chatRoom = (ChatRoom) o;
        return Objects.equals(id, chatRoom.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public int compareTo(ChatRoom o) {
        if (getLast_update() == null || o.getLast_update() == null)
            return 0;
        return getLast_update().compareTo(o.getLast_update());
    }
}