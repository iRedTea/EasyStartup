package site.easystartup.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import site.easystartup.web.domain.model.User;
import site.easystartup.web.repo.UserRepo;
import site.easystartup.web.request.UserRequest;
import site.easystartup.web.storage.service.StorageService;

import java.security.Principal;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;

    private final StorageService storageService;

    public User getUserByPrincipal(Principal principal) {
        if(principal == null) return getCurrent();
        return userRepo.findByUsername(principal.getName());
    }

    public User getUserById(Long userId) {
        return userRepo.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public boolean userExists(String username) {
        return userRepo.findUserByUsername(username).isPresent();
    }


    public User getUserByUsername(String username) {
        return userRepo.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User editUser(String username, UserRequest userRequest) {
        User user = userRepo.findByUsername(username);
        user.setActive(userRequest.isActive());
        user.setFull_name(userRequest.getFull_name());
        user.setStatus(userRequest.getStatus());

        if(userRequest.getIcon() != null) storageService.saveImage(userRequest.getIcon(), username + "-icon");
        user.setIconPath(user.getIconPath() == null ? "" : username + "-icon.jpg");
        user.setEmail(userRequest.getEmail() == null ? "" : userRequest.getEmail());
        user.setTechnologies(userRequest.getTags() == null ? Collections.emptySet() : userRequest.getTags());
        user.setProfessions(userRequest.getProfessions() == null ? Collections.emptySet() : userRequest.getProfessions());
        user.setProjects(userRequest.getProjects() == null ? Collections.emptySet() : userRequest.getProjects());
        user.setRequests(userRequest.getRequests() == null ? Collections.emptyList() : userRequest.getRequests());
        user.setAbout(userRequest.getAbout() == null ? "" : userRequest.getAbout());
        user.setCountry(userRequest.getCountry() == null ? "" : userRequest.getCountry());
        user.setCity(userRequest.getCity() == null ? "" : userRequest.getCity());
        user.setLangs(userRequest.getLangs() == null ? Collections.emptySet() : userRequest.getLangs());
        user.setContacts(userRequest.getContacts() == null ? Collections.emptyMap() : userRequest.getContacts());
        return userRepo.save(user);
    }

    public User getCurrent() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return getUserByUsername(login);
    }
}
