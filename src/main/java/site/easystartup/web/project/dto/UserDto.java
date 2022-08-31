package site.easystartup.web.project.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import site.easystartup.web.domain.model.Role;

import java.io.Serializable;
import java.util.Set;

@Data
@NoArgsConstructor
public class UserDto implements Serializable {
    private  long id;
    private  String username;
    private  String password;
    private  boolean active;
    private  String full_name;
    private  String status;
    private  String iconPath;
    private  String email;
    private  Set<String> tags;
    private  Set<String> professions;
    private  Set<Long> projects;
    private  Set<Role> roles;
}
