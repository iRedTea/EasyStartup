package site.easystartup.easystartupcore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.easystartup.easystartupcore.domain.User;
import site.easystartup.easystartupcore.repo.UserRepo;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;



    public User getUserByPrincipal(Principal principal) {
        return userRepo.findByUsername(principal.getName());
    }
}
