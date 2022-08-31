package site.easystartup.web.notification.event.realtime;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import site.easystartup.web.notification.domian.Notification;
import site.easystartup.web.notification.event.EventHandler;
import site.easystartup.web.notification.event.NotificationEvent;
import site.easystartup.web.notification.event.NotificationListener;
import site.easystartup.web.notification.service.NotificationService;
import site.easystartup.web.post.domain.event.RepostEvent;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RealTimeListener implements NotificationListener {
    private final NotificationService notificationService;

    //Обрабатывает все ивенты
    @EventHandler
    public void onEvent(NotificationEvent event) {
        notificationService.sendNotification(
                new Notification(event.getMessage()));
    }
}
