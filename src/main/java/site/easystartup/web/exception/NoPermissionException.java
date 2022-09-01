package site.easystartup.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class NoPermissionException extends RuntimeException {
    public NoPermissionException(String username) {
        super("User " + username + " do not have access for this action");
    }
}
