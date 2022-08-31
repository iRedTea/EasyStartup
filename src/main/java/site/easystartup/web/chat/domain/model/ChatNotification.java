package site.easystartup.web.chat.domain.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ChatNotification {
    private long id;
    private String senderId;
    private String senderName;
}