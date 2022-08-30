package site.easystartup.web.forum.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.function.Supplier;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TopicNotFoundException extends RuntimeException implements Supplier<TopicNotFoundException> {
    public TopicNotFoundException(String message) {
        super(message);
    }

    @Override
    public TopicNotFoundException get() {
        return this;
    }
}
