package site.easystartup.web.notification.event;

public interface NotificationEvent {
    void invoke();

    String getRecipient();

    String getMessage();
}
