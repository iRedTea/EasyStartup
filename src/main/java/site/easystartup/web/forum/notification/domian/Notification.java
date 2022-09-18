package site.easystartup.web.forum.notification.domian;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@ToString
@Entity
public class Notification {
    @Id
    private long id;
    private String text;
    private String recipient;
}
