package site.easystartup.web.domain.request;

import lombok.Data;
import site.easystartup.web.domain.annotation.PasswordMatches;
import site.easystartup.web.domain.model.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@PasswordMatches
public class SignupRequest {
    @NotEmpty
    @Size(min = 3, max = 30, message = "Username should be no less 3 and no more 30 signs")
    private String username;

    @Email(message = "It should have email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotEmpty(message = "Password is required")
    @Size(min = 6, max = 30, message = "Password should be no less 6 and no more 30 signs")
    private String password;

    private String confirmPassword;

    private Set<String> roles;
}