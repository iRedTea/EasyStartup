package site.easystartup.web.domain.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class InvalidLoginResponse {
    private String username;
    private String password;

    public InvalidLoginResponse() {
        this.username = "Invalid username";
        this.password = "Invalid password";
    }
}
