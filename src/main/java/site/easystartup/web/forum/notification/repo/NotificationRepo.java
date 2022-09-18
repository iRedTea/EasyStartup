package site.easystartup.web.forum.notification.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import site.easystartup.web.forum.notification.domian.Notification;

public interface NotificationRepo extends JpaRepository<Notification, Long> {
}
