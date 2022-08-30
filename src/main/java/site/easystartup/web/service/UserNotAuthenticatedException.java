package site.easystartup.web.service;

public class UserNotAuthenticatedException extends RuntimeException {
    public UserNotAuthenticatedException() {
        super("The user is not logged in to the system!");
    }
}
