package site.easystartup.web.post.domain.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import site.easystartup.web.notification.event.NotificationEvent;
import site.easystartup.web.notification.event.NotificationEventManager;

@RequiredArgsConstructor
public class RepostEvent implements NotificationEvent {
    @Autowired
    private NotificationEventManager notificationEventManager;

    @Getter
    private final String recipient;

    @Getter
    private final String reportedUser;

    @Getter
    private final String message;

    @Override
    public void invoke() {
        notificationEventManager.notifyListeners(getClass());
    }
}
