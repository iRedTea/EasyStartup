package site.easystartup.web.post.domain.exception;

public class DontLickedException extends RuntimeException{
    public DontLickedException(String message) {
        super(message);
    }
}
