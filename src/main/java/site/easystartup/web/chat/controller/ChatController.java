package site.easystartup.web.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import site.easystartup.web.chat.domain.model.ChatMessage;
import site.easystartup.web.chat.domain.model.ChatNotification;
import site.easystartup.web.chat.domain.request.ChatMessageRequest;
import site.easystartup.web.chat.repo.ChatMessageRepo;
import site.easystartup.web.chat.service.ChatService;

import java.util.Date;

@Controller
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;

    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessageRequest chatMessageRequest) {
        ChatMessage chatMessage = chatService.getByRequest(chatMessageRequest);
        var room = chatService
                .getChatRoom(chatMessage.getRecipientName(), chatMessage.getRecipientId());
        chatMessage.setChatId(room.getChatId());
        room.setLast_update(new Date());

        ChatMessage saved = chatService.save(chatMessage);

        messagingTemplate.convertAndSendToUser(
                chatMessage.getRecipientId(),"/queue/messages",
                new ChatNotification(
                        saved.getId(),
                        saved.getSenderId(),
                        saved.getSenderName()));
    }
}