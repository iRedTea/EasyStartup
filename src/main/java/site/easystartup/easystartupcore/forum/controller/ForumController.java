package site.easystartup.easystartupcore.forum.controller;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import site.easystartup.easystartupcore.forum.domain.Topic;
import site.easystartup.easystartupcore.forum.filter.ForumFilterService;
import site.easystartup.easystartupcore.forum.repo.TopicRepo;

import java.util.Date;

@Controller
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ForumController {
    private final TopicRepo topicRepo;

    private final ForumFilterService forumFilterService;

    @GetMapping("/forum")
    public String forumMain(Model model) {
        val topic = topicRepo.findAll();
        model.addAttribute("topics", topic);
        return "/forum";
    }

    @GetMapping("/forum/my")
    public String forumMy(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        val discussions = forumFilterService.getDiscussionsByAuthor(user.getUsername());
        model.addAttribute("discussions", discussions);
        return "/my-discussions";
    }

    @GetMapping("/forum/new")
    public String forumNew() {
        return "topic-new";
    }

    @PostMapping("/forum/new")
    public String forumNewTopic(@RequestParam String title,
                               @RequestParam String description) {
        description = description == null ? "" : description;
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Topic topic = new Topic();
        topic.setTitle(title);
        topic.setDescription(description);
        topic.setAuthor(user.getUsername());
        topic.setLast_update(new Date());
        topic.setSize(0);
        topicRepo.save(topic);
        return "/forum";
    }

    @GetMapping("/forum/{topic_id}")
    public String topicFirstPage(@PathVariable(value = "topic_id") long topic_id,
                              Model model) {
        val pinned_discussions = forumFilterService.getPinnedDiscussionsByTopicId(topic_id);
        model.addAttribute("pinned_discussions", pinned_discussions);
        val discussions = forumFilterService.getSortedDiscussionsByTopicId(topic_id).subList(0, 15);
        model.addAttribute("discussions", discussions);
        return "topic-first";
    }

    @GetMapping("/forum/{topic_id}/page/{page_index}")
    public String topicPage(@PathVariable(value = "topic_id") long topic_id,
                            @PathVariable(value = "topic_id") long page_index,
                              Model model) {
        val pinned_discussions = forumFilterService.getPinnedDiscussionsByTopicId(topic_id);
        model.addAttribute("pinned_discussions", pinned_discussions);
        val discussions = forumFilterService.getSortedDiscussionsByTopicId(topic_id).subList(0, 15);
        model.addAttribute("discussions", discussions);
        return "topic-first";
    }
}
