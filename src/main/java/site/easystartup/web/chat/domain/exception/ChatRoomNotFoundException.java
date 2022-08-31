package site.easystartup.web.chat.domain.exception;

import java.util.function.Supplier;

public class ChatRoomNotFoundException extends RuntimeException implements Supplier<ChatRoomNotFoundException> {
    public ChatRoomNotFoundException(String message) {
        super(message);
    }

    @Override
    public ChatRoomNotFoundException get() {
        return this;
    }
}
