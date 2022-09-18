package site.easystartup.web.forum.notification.event;

public interface NotificationEvent {
    void invoke();

    String getRecipient();

    String getMessage();
}
