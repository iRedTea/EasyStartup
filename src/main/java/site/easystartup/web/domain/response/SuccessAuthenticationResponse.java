package site.easystartup.web.domain.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import site.easystartup.web.project.dto.UserDto;

@Getter
@AllArgsConstructor
public class SuccessAuthenticationResponse {
    private String jwt;
    private UserDto userDto;
}
