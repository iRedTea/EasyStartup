package site.easystartup.web.forum.notification.event.realtime;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import site.easystartup.web.forum.notification.domian.Notification;
import site.easystartup.web.forum.notification.service.NotificationService;
import site.easystartup.web.forum.notification.event.EventHandler;
import site.easystartup.web.forum.notification.event.NotificationEvent;
import site.easystartup.web.forum.notification.event.NotificationListener;
import site.easystartup.web.forum.notification.repo.NotificationRepo;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RealTimeListener implements NotificationListener {
    private final NotificationService notificationService;

    private final NotificationRepo repo;

    @EventHandler
    public void onEvent(NotificationEvent event) {
        Notification notification = new Notification();
        notification.setText(event.getMessage());
        notification.setRecipient(event.getRecipient());
        notificationService.sendNotification(repo.save(notification));
    }
}
