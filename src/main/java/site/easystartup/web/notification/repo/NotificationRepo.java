package site.easystartup.web.notification.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import site.easystartup.web.notification.domian.Notification;

public interface NotificationRepo extends JpaRepository<Notification, Long> {
}
