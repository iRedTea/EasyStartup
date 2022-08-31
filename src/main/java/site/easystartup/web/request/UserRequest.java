package site.easystartup.web.request;

import lombok.Data;
import site.easystartup.web.domain.model.Role;
import site.easystartup.web.project.domain.model.Participant;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Data
public class UserRequest {
    private long id;
    private String username;

    @NotNull
    private boolean active;

    @NotNull
    private String full_name;

    @Size(max = 60, message = "Status should be no more 60 signs")
    @NotNull
    private String status;

    private String iconPath;

    @Email(message = "It should have email format")
    @NotBlank(message = "Email is required")
    private String email;

    private Set<String> tags;
    private Set<String> professions;
    private Set<Long> projects;
    private List<Participant> requests;
    private Set<Role> roles;
}
