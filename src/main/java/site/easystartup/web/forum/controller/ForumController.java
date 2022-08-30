package site.easystartup.web.forum.controller;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import site.easystartup.web.domain.User;
import site.easystartup.web.forum.domain.Discussion;
import site.easystartup.web.forum.domain.DiscussionMessage;
import site.easystartup.web.forum.domain.DiscussionStatus;
import site.easystartup.web.forum.domain.Topic;
import site.easystartup.web.forum.service.ForumService;
import site.easystartup.web.forum.repo.TopicRepo;
import site.easystartup.web.service.UserService;

import java.util.Date;

@Controller
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ForumController {
    private final TopicRepo topicRepo;

    private final ForumService forumService;

    private final UserService userService;

    @GetMapping("/forum")
    public String forumMain(Model model) {
        val topic = topicRepo.findAll();
        model.addAttribute("topics", topic);
        return "/forum";
    }

    @GetMapping("/forum/my")
    public String forumMy(Model model) {
        User user = userService.getCurrentUser();
        val discussions = forumService.getDiscussionsByAuthor(user.getUsername());
        model.addAttribute("discussions", discussions);
        return "/my-discussions";
    }

    @GetMapping("/forum/new")
    public String forumNew() {
        return "topic-new";
    }

    @Secured({"ADMIN", "MODER"})
    @PostMapping("/forum/new")
    public String forumNewTopic(@RequestParam String title,
                               @RequestParam String description) {
        forumService.createTopic(title, description, userService.getCurrentUser());
        return "/forum";

    }

    @GetMapping("/forum/{topic_id}")
    public String topicFirstPage(@PathVariable(value = "topic_id") long topic_id,
                              Model model) {
        val pinned_discussions = forumService.getPinnedDiscussionsByTopicId(topic_id);
        model.addAttribute("pinned_discussions", pinned_discussions);
        val discussions = forumService.getSortedDiscussionsByTopicId(topic_id).subList(0, 14);
        model.addAttribute("discussions", discussions);
        return "/topic-first";
    }

    @GetMapping("/forum/{topic_id}/page/{page_index}")
    public String topicPage(@PathVariable(value = "topic_id") long topic_id,
                            @PathVariable(value = "page_index") long page_index,
                            Model model) {
        val discussions = forumService.getSortedDiscussionsByTopicId(topic_id).subList((int)(15 * page_index), (int)(15 * page_index) + 15);
        model.addAttribute("discussions", discussions);
        return "/topic-content";
    }

    @GetMapping("/forum/discussion/{discussion_id}")
    public String discussion(@PathVariable(value = "discussion_id") long discussion_id,
                                 Model model) {
        model.addAttribute("discussion", forumService.getDiscussionById(discussion_id));
        model.addAttribute("messages ", forumService.getMessagesByDiscussionId(discussion_id));
        return "/discussion";
    }

    @GetMapping("/forum/{topic_id}/create")
    public String discussionCreate(@PathVariable long topic_id) {
        return "/discussion-create";
    }

    @PostMapping("/forum/{topic_id}/create")
    public String discussionCreatePost(@PathVariable long topic_id,
            @RequestParam String title) {
        forumService.createDiscussion(title, topic_id, userService.getCurrentUser());
        return "/discussion-create";
    }

    @GetMapping("/forum/discussion/{discussion_id}/edit")
    public String discussionEdit(@PathVariable long discussion_id, Model model) {
        Discussion discussion = forumService.getDiscussionById(discussion_id);
        User user = userService.getCurrentUser();
        if(!(discussion.getAuthor().equals(user.getUsername()) || user.isModer())) return "/403-error";
        model.addAttribute("discussion", discussion);
        return "/discussion-edit";
    }

    @PostMapping("/forum/discussion/{discussion_id}/edit")
    public String discussionEditPost(@PathVariable long discussion_id,
                                       @RequestParam String title,
                                       @RequestParam DiscussionStatus status,
                                       Model model) {
        forumService.editDiscussion(title, discussion_id, status, userService.getCurrentUser());
        model.addAttribute("discussion", forumService.getDiscussionById(discussion_id));
        model.addAttribute("messages ", forumService.getMessagesByDiscussionId(discussion_id));
        return "/discussion";
    }

    @GetMapping("/forum/discussion/{discussion_id}/add")
    public String messageAdd(@PathVariable long discussion_id) {
        return "/discussion-message-add";
    }

    @PostMapping("/forum/discussion/{discussion_id}/add")
    public String messageAddPost(@PathVariable long discussion_id,
                                       @RequestParam String text,
                                       Model model) {
        forumService.addDiscussionMessage(text, discussion_id, userService.getCurrentUser());
        model.addAttribute("discussion", forumService.getDiscussionById(discussion_id));
        model.addAttribute("messages ", forumService.getMessagesByDiscussionId(discussion_id));
        return "/discussion";
    }

    @GetMapping("/forum/discussion/{discussion_id}/{message_id}/edit")
    public String messageEdit(@PathVariable long discussion_id,
                              @PathVariable long message_id) {
        DiscussionMessage message = forumService.getDiscussionMessageById(message_id);
        User user = userService.getCurrentUser();
        if(!(message.getSender().equals(user.getUsername()) || user.isModer())) return "/403-error";
        return "/discussion-message-edit";
    }

    @PostMapping("/forum/discussion/{discussion_id}/{message_id}/edit")
    public String messageEditPost(@PathVariable long discussion_id,
                                  @RequestParam String text,
                                  Model model, @PathVariable String message_id) {
        forumService.editDiscussionMessage(text, discussion_id, userService.getCurrentUser());
        model.addAttribute("discussion", forumService.getDiscussionById(discussion_id));
        model.addAttribute("messages ", forumService.getMessagesByDiscussionId(discussion_id));
        return "/discussion";
    }

    @DeleteMapping("/forum/discussion/{discussion_id}/{message_id}/delete")
    public String messageDelete(@PathVariable long discussion_id,
                              @PathVariable long message_id,
                                Model model) {
        DiscussionMessage message = forumService.getDiscussionMessageById(message_id);
        User user = userService.getCurrentUser();
        if(!(message.getSender().equals(user.getUsername()) || user.isModer())) return "/403-error";

        model.addAttribute("discussion", forumService.getDiscussionById(discussion_id));
        model.addAttribute("messages ", forumService.getMessagesByDiscussionId(discussion_id));
        return "/discussion";
    }


}
