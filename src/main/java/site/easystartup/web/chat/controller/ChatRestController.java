package site.easystartup.web.chat.controller;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.easystartup.web.chat.dto.ChatMessageDto;
import site.easystartup.web.chat.dto.ChatRoomDto;
import site.easystartup.web.chat.service.ChatService;
import site.easystartup.web.exception.NoPermissionException;
import site.easystartup.web.service.UserService;

import java.security.Principal;
import java.util.List;

@RestController @CrossOrigin(origins = {"212.76.129.195:9990", "212.76.129.195"})
@RequestMapping("/chat")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ChatRestController {
    private final ChatService chatService;

    private final ModelMapper modelMapper;

    private final UserService userService;

    @GetMapping("/{chat_id}")
    public ResponseEntity<List<ChatMessageDto>> chatId(@PathVariable String chat_id,
                                                       Principal principal) {
        var chatMessages = chatService.getByChatId(chat_id, principal)
                .stream().map(message -> modelMapper.map(message, ChatMessageDto.class)).toList();
        return ResponseEntity.ok().body(chatMessages);
    }

    @GetMapping("/see")
    public ResponseEntity<List<ChatMessageDto>> chat(@RequestParam String sender,
                                                       @RequestParam String recipient,
                                                       Principal principal) {
        var chatMessages = chatService.getChatMessages(sender, recipient, principal)
                .stream().map(message -> modelMapper.map(message, ChatMessageDto.class)).toList();
        return ResponseEntity.ok().body(chatMessages);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<ChatRoomDto>> user(@PathVariable String username,
                                                  Principal principal) {
        var chatMessages = chatService.getChats(username, principal)
                .stream().map(message -> modelMapper.map(message, ChatRoomDto.class)).toList();
        return ResponseEntity.ok().body(chatMessages);
    }
}
