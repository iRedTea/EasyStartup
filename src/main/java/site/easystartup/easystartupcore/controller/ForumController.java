package site.easystartup.easystartupcore.controller;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import site.easystartup.easystartupcore.domain.forum.Discussion;
import site.easystartup.easystartupcore.domain.forum.Topic;
import site.easystartup.easystartupcore.repos.forum.TopicRepo;

import java.util.Collections;

@Controller
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ForumController {
    private final TopicRepo topicRepo;

    private final Discussion discussion;

    @GetMapping("/forum")
    public String forumMain(Model model) {
        val topic = topicRepo.findAll();
        model.addAttribute("topics", topic);
        return "forum";
    }

    @GetMapping("/forum/my")
    public String forumMy(Model model) {
        val topic = Lists.newArrayList(topicRepo.findAll());
        model.addAttribute("topics", topic);
        return "forum";
    }
}
