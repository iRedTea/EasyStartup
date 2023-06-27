package site.easystartup.web.util;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import site.easystartup.web.service.UserService;

@Configuration
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FastChecksUtil {
    private final UserService userService;

    public<T> ResponseEntity<T> nullIfValid(Long user) {
        if(!userService.userExists(user)) {
            return ResponseEntity.notFound().build();
        }
        return null;
    }

}
