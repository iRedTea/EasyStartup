package site.easystartup.web.chat.domain.model;

import lombok.Data;

@Data
public class ChatRoom {
    private String id;
    private String chatId;
    private String senderId;
    private String recipientId;
}