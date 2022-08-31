package site.easystartup.web.post.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.function.Supplier;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PostNotFoundException extends RuntimeException implements Supplier<PostNotFoundException> {
    private String message;

    public PostNotFoundException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public PostNotFoundException get() {
        return this;
    }
}
