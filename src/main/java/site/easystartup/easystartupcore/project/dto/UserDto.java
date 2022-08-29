package site.easystartup.easystartupcore.project.dto;

import lombok.Data;
import site.easystartup.easystartupcore.domain.Role;

import java.io.Serializable;
import java.util.Set;

@Data
public class UserDto implements Serializable {
    private final long id;
    private final String username;
    private final String password;
    private final boolean active;
    private final String full_name;
    private final String status;
    private final String iconPath;
    private final String email;
    private final Set<String> tags;
    private final Set<String> professions;
    private final Set<Long> projects;
    private final Set<Role> roles;
}
