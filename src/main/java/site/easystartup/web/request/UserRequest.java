package site.easystartup.web.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import site.easystartup.web.domain.model.Role;
import site.easystartup.web.project.domain.model.Participant;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
public class UserRequest {
    @NotNull
    private boolean active;

    @NotNull
    private String full_name;

    @Size(max = 60, message = "Status should be no more 60 signs")
    private String status;

    private MultipartFile icon;

    @Email(message = "It should have email format")
    @NotBlank(message = "Email is required")
    private String email;

    private Set<String> tags;
    private Set<String> professions;
    private Set<Long> projects;
    private List<Participant> requests;
    private Set<Role> roles;
    @Size(max = 1000, message = "Status should be no more 1000 signs")
    private String about;
    @Size(max = 100, message = "Status should be no more 100 signs")
    private String country;
    @Size(max = 100, message = "Status should be no more 100 signs")
    private String city;
    private Set<String> langs;
    private Map<String, String> contacts;
}
