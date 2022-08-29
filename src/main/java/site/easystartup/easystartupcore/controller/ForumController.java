package site.easystartup.easystartupcore.controller;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import site.easystartup.easystartupcore.domain.forum.Discussion;
import site.easystartup.easystartupcore.domain.forum.Topic;
import site.easystartup.easystartupcore.filter.FilterService;
import site.easystartup.easystartupcore.repos.forum.TopicRepo;

import java.util.Collections;

@Controller
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ForumController {
    private final TopicRepo topicRepo;

    private final FilterService filterService;

    @GetMapping("/forum")
    public String forumMain(Model model) {
        val topic = topicRepo.findAll();
        model.addAttribute("topics", topic);
        return "forum";
    }

    @GetMapping("/forum/my")
    public String forumMy(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        val discussions = filterService.getDiscussionsByAuthor(user.getUsername());
        model.addAttribute("discussions", discussions);
        return "my-discussions";
    }
}
