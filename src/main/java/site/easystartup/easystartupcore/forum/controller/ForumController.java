package site.easystartup.easystartupcore.forum.controller;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import site.easystartup.easystartupcore.forum.filter.FilterService;
import site.easystartup.easystartupcore.forum.repo.TopicRepo;

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

    @GetMapping("/forum/new")
    public String forumNew() {
        return "topic-new";
    }
}
