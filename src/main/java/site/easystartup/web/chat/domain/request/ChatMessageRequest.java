package site.easystartup.web.chat.domain.request;

import lombok.Data;
import site.easystartup.web.chat.domain.model.MessageStatus;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class ChatMessageRequest {
    private long id;
    private String chatId;
    @NotNull
    private String senderId;
    @NotNull
    private String recipientId;
    @NotNull
    private String senderName;
    @NotNull
    private String recipientName;
    @NotNull
    @Size(min = 1, max = 255, message = "Text should be no less 1 and no more 255 signs")
    private String content;
    private Date timestamp;
    private MessageStatus status;
}
