package site.easystartup.web.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.query.criteria.internal.predicate.PredicateImplementor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import site.easystartup.web.domain.User;
import site.easystartup.web.repo.UserRepo;

import java.security.Principal;

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
}
