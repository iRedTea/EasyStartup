package site.easystartup.web.dto;

import lombok.Data;
import site.easystartup.web.domain.model.Role;
import site.easystartup.web.project.domain.model.Participant;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
public class UserDto implements Serializable {
    private long id;
    private String username;
    private boolean active;
    private String full_name;
    private String status;
    private String iconPath;
    private String email;
    private Set<String> tags;
    private Set<String> professions;
    private Set<Long> projects;
    private List<Participant> requests;
    private Set<Role> roles;
}
