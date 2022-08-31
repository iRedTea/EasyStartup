package site.easystartup.web.chat.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatNotification {
    private String id;
    private String senderName;
}