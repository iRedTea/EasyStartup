package site.easystartup.web.domain.request;

import lombok.Data;
import site.easystartup.web.domain.annotation.PasswordMatches;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@PasswordMatches
public class UpdatePassportRequest {
    @NotEmpty(message = "Password is required")
    @Size(min = 6, max = 30, message = "Password should be no less 6 and no more 30 signs")
    private String password;

    private String confirmPassword;
}
