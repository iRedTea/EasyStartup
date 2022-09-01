package site.easystartup.web.post.domain.exception;

public class AlreadyLikedException extends RuntimeException {
    public AlreadyLikedException(String message) {
        super(message);
    }
}
