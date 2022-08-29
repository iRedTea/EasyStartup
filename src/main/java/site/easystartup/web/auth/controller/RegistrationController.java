package site.easystartup.web.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import site.easystartup.web.domain.Role;
import site.easystartup.web.domain.User;
import site.easystartup.web.repo.UserRepo;

import java.util.Collections;

@Controller
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RegistrationController {
    private final PasswordEncoder passwordEncoder;

    private final UserRepo userRepo;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Model model) {
        User userFromDb = userRepo.findByUsername(user.getUsername());

        if(userFromDb != null) {
            model.addAttribute("message", "Пользователь уже существует!");
            return "registration";
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepo.save(user);

        return "redirect:/login";
    }
}
