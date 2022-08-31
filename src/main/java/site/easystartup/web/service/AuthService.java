package site.easystartup.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import site.easystartup.web.domain.exception.UserExistException;
import site.easystartup.web.domain.model.Role;
import site.easystartup.web.domain.model.User;
import site.easystartup.web.domain.request.SignupRequest;
import site.easystartup.web.repo.UserRepo;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public User createUser(SignupRequest signupRequest) {
        if (userRepo.findUserByUsername(signupRequest.getUsername()).isPresent()) {
            throw new UserExistException("An account with this username already exists.");
        }

        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setUsername(signupRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        Set<Role> roles = new HashSet<>();
        signupRequest.getRoles().forEach(role -> roles.add(Role.valueOf(role)));
        user.setRoles(roles);

        return userRepo.save(user);
    }
}
