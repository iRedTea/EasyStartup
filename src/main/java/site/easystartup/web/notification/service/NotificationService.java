package site.easystartup.web.notification.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import site.easystartup.web.notification.domian.Notification;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class NotificationService {
    private final SimpMessagingTemplate messagingTemplate;

    public void sendNotification(Notification notification) {
        messagingTemplate.convertAndSendToUser("server", "queue/notification", notification);
    }
}
