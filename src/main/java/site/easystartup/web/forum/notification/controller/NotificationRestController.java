package site.easystartup.web.forum.notification.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.easystartup.web.forum.notification.domian.Notification;
import site.easystartup.web.forum.notification.service.NotificationService;

import java.security.Principal;
import java.util.List;

@RestController @CrossOrigin(origins = {"212.76.129.195:9990", "212.76.129.195"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class NotificationRestController {
    private final NotificationService notificationService;

    @GetMapping("/notifications/{username}")
    public ResponseEntity<List<Notification>> notification(@PathVariable String username, Principal principal) {
        return ResponseEntity.ok().body(notificationService.getNotificationByRecipient(username, principal));
    }

    @DeleteMapping("/notifications/{id}/delete")
    public void delete(@PathVariable long id,
                       Principal principal) {
        notificationService.delete(id, principal);
    }
}
