package site.easystartup.web.notification.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import site.easystartup.web.exception.NoPermissionException;
import site.easystartup.web.notification.domian.Notification;
import site.easystartup.web.notification.repo.NotificationRepo;
import site.easystartup.web.service.UserService;

import java.security.Principal;
import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class NotificationService {
    private final SimpMessagingTemplate messagingTemplate;

    private final NotificationRepo repo;

    private final UserService userService;

    public void sendNotification(Notification notification) {
        messagingTemplate.convertAndSendToUser(notification.getRecipient(), "queue/notification", notification);
    }

    public List<Notification> getNotificationByRecipient(String recipient, Principal principal) {
        if(!recipient.equals(principal.getName()) && !userService.getUserByPrincipal(principal).isAdmin())
            throw new NoPermissionException(String.format("User %s dont have access to see notifications of user %s",
                    principal.getName(), recipient));
        return repo.findAll().stream()
                .filter(notification -> notification.getRecipient().equals(recipient)).toList();
    }

    public void delete(long id, Principal principal) {
        Notification notification = repo.findById(id).orElseThrow();
        if(!notification.getRecipient().equals(principal.getName()) && !userService.getUserByPrincipal(principal).isAdmin())
            throw new NoPermissionException(String.format("User %s dont have access to delete notifications of user %s",
                    principal.getName(), notification.getRecipient()));
        repo.delete(notification);
    }
}
