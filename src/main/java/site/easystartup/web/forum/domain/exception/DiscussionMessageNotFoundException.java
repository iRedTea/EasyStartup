package site.easystartup.web.forum.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.function.Supplier;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DiscussionMessageNotFoundException extends RuntimeException implements Supplier<DiscussionMessageNotFoundException> {
    private String message;

    public DiscussionMessageNotFoundException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public DiscussionMessageNotFoundException get() {
        return this;
    }
}
