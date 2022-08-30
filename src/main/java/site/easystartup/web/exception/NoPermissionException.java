package site.easystartup.web.exception;

public class NoPermissionException extends RuntimeException {
    public NoPermissionException(String username) {
        super("User " + username + " do not have access for this action");
    }
}
