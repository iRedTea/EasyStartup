package site.easystartup.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import site.easystartup.web.domain.model.User;
import site.easystartup.web.repo.UserRepo;
import site.easystartup.web.request.UserRequest;

import java.security.Principal;
import java.util.TreeSet;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;

    public User getUserByPrincipal(Principal principal) {
        return userRepo.findByUsername(principal.getName());
    }

    public User getUserById(Long userId) {
        return userRepo.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User getUserByUsername(String username) {
        return userRepo.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User editUser(String username, UserRequest userRequest) {
        User user = userRepo.findByUsername(username);
        user.setActive(userRequest.isActive());
        user.setFull_name(userRequest.getFull_name());
        user.setStatus(userRequest.getStatus());
        user.setIconPath(user.getIconPath() == null ? user.getIconPath() : userRequest.getIconPath());
        user.setEmail(userRequest.getEmail() == null ? user.getEmail() : userRequest.getEmail());
        user.setTags(userRequest.getTags() == null ? user.getTags() : userRequest.getTags());
        user.setProfessions(userRequest.getProfessions() == null ? user.getProfessions() : userRequest.getProfessions());
        user.setProjects(userRequest.getProjects() == null ? user.getProjects() : userRequest.getProjects());
        user.setRequests(userRequest.getRequests() == null ? user.getRequests() : userRequest.getRequests());
        return userRepo.save(user);
    }
}
