package site.easystartup.web.chat.dto;

import lombok.Data;

@Data
public class ChatRoomDto {
    private long id;
    private String chatId;
    private String sender;
    private String recipient;
}
