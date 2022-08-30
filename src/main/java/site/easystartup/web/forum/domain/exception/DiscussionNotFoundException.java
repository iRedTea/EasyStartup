package site.easystartup.web.forum.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.function.Supplier;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DiscussionNotFoundException extends RuntimeException implements Supplier<DiscussionNotFoundException> {
    public DiscussionNotFoundException(String message) {
        super(message);
    }

    @Override
    public DiscussionNotFoundException get() {
        return this;
    }
}
