package site.easystartup.web.notification.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import site.easystartup.web.notification.domian.Notification;

import java.security.Principal;
import java.util.logging.Logger;

@Controller
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class NotificationController {
    private final SimpMessagingTemplate messagingTemplate;
    Logger logger = Logger.getGlobal();

    @GetMapping("/notify")
    public void notification(Principal principal, @Payload Notification notification) {
        logger.info("Web socket sent notification to " + principal.getName());
        messagingTemplate.convertAndSendToUser(principal.getName(), "queue/notification", notification);
    }



}
