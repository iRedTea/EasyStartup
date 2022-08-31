package site.easystartup.web.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import site.easystartup.web.chat.domain.exception.ChatRoomNotFoundException;
import site.easystartup.web.chat.domain.model.ChatMessage;
import site.easystartup.web.chat.domain.model.ChatRoom;
import site.easystartup.web.chat.domain.request.ChatMessageRequest;
import site.easystartup.web.chat.dto.ChatRoomDto;
import site.easystartup.web.chat.repo.ChatMessageRepo;
import site.easystartup.web.chat.repo.ChatRoomRepo;
import site.easystartup.web.exception.NoPermissionException;
import site.easystartup.web.service.UserService;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
@Scope("singleton")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ChatService {
    private final ChatMessageRepo chatMessageRepo;
    private final ChatRoomRepo chatRoomRepo;
    private final UserService userService;

    public ChatRoom getChatRoom(String sender, String recipient) {
        return chatRoomRepo.findAll().stream()
                .filter(chatRoom -> chatRoom.getSender().equals(sender)
                && chatRoom.getRecipient().equals(recipient)).findAny()
                .orElse(createChatRoom(sender, recipient));
    }

    public List<ChatMessage> getChatMessages(String sender, String recipient, Principal principal) {
        if(!getChatRoom(sender, recipient).getSender().equals(principal.getName())
                || !(userService.getUserByPrincipal(principal)).isAdmin())
            throw new NoPermissionException(
                    String.format("User %s dont have perms to see chat %s / %s", sender, recipient));

        List<ChatMessage> result = new java.util.ArrayList<>(chatMessageRepo.findAll().stream().filter(
                chatMessage -> chatMessage.getSenderName().equals(sender) && chatMessage.getSenderName().equals(recipient)
        ).toList());
        result.sort(ChatMessage::compareTo);
        return result;
    }

    public List<ChatRoom> getChats(String sender, Principal principal) {
        if(!principal.getName().equals(sender)
                || !(userService.getUserByPrincipal(principal)).isAdmin())
            throw new NoPermissionException(
                    String.format("User %s dont have perms to see chats %s", sender));

        List<ChatRoom> result = new java.util.ArrayList<>(chatRoomRepo.findAll().stream().filter(
                chatRoom -> chatRoom.getSender().equals(sender)
        ).toList());
        result.sort(ChatRoom::compareTo);
        return result;
    }

    public ChatRoom getChatRoom(String chat_id) {
        return chatRoomRepo.findAll().stream()
                .filter(chatRoom -> chatRoom.getChatId().equals(chat_id)).findAny()
                .orElseThrow(new ChatRoomNotFoundException("Chat with id " + chat_id + " dont exists!"));
    }

    public ChatMessage getByRequest(ChatMessageRequest chatMessageRequest) {
        ChatMessage result = new ChatMessage();
        result.setContent(chatMessageRequest.getContent());
        result.setRecipientId(chatMessageRequest.getRecipientId());
        result.setRecipientName(chatMessageRequest.getRecipientName());
        result.setSenderId(chatMessageRequest.getSenderId());
        result.setSenderName(chatMessageRequest.getSenderName());
        result.setContent(chatMessageRequest.getContent());
        result.setTimestamp(chatMessageRequest.getTimestamp() != null ? chatMessageRequest.getTimestamp() : new Date());
        return result;
    }

    public ChatMessage save(ChatMessage chatMessage) {
        return chatMessageRepo.save(chatMessage);
    }

    public List<ChatMessage> getByChatId(String chat_id, Principal principal) {
        if(!getChatRoom(chat_id).getSender().equals(principal.getName())
                || !(userService.getUserByPrincipal(principal)).isAdmin())
            throw new NoPermissionException(
                    String.format("User %s dont have perms to see chat %s", principal.getName(), chat_id));

        return chatMessageRepo.findAll().stream().filter(chatMessage -> chatMessage.getChatId().equals(chat_id)).toList();
    }

    public ChatRoom createChatRoom(String sender, String recipient) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setSender(sender);
        chatRoom.setRecipient(recipient);
        chatRoom.setChatId(UUID.randomUUID().toString());
        return chatRoomRepo.save(chatRoom);
    }
}
