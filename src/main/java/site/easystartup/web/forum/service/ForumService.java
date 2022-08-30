package site.easystartup.web.forum.service;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import site.easystartup.web.domain.Role;
import site.easystartup.web.domain.User;
import site.easystartup.web.forum.domain.Discussion;
import site.easystartup.web.forum.domain.DiscussionMessage;
import site.easystartup.web.forum.domain.DiscussionStatus;
import site.easystartup.web.forum.domain.Topic;
import site.easystartup.web.forum.repo.DiscussionMessageRepo;
import site.easystartup.web.forum.repo.DiscussionRepo;
import site.easystartup.web.forum.repo.TopicRepo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ForumService {
    private final TopicRepo topicRepo;

    private final DiscussionMessageRepo discussionMessageRepo;

    private final DiscussionRepo discussionRepo;

    public List<DiscussionMessage> getMessagesByDiscussionId(long discussionId) {
        return Lists.newArrayList(discussionMessageRepo.findAll()).stream()
                .filter(m -> m.getDiscussion_id() == discussionId).toList();
    }

    public Discussion getDiscussionById(long discussionId) {
        return discussionRepo.findById(discussionId).get();
    }

    public List<Discussion> getDiscussionsByAuthor(String author) {
        return Lists.newArrayList(discussionRepo.findAll()).stream()
                .filter(d -> d.getAuthor().equals(author)).toList();
    }

    public List<Discussion> getDiscussionsByMember(String member) {
         return Lists.newArrayList(discussionRepo.findAll()).stream()
                .filter(d -> getMessagesByDiscussionId(d.getId()).stream()
                        .anyMatch(m -> m.getSender().equals(member))).toList();
    }

    public List<Discussion> getPinnedDiscussionsByTopicId(long topic_id) {
        return Lists.newArrayList(discussionRepo.findAll()).stream()
                .filter(Discussion::isPinned)
                .filter(d -> d.getTopic() == topic_id).toList();
    }

    public List<Discussion> getSortedDiscussionsByTopicId(long topic_id) {
        List<Discussion> result = Lists.newArrayList(discussionRepo.findAll()).stream()
                .filter(d -> !d.isPinned())
                .filter(d -> d.getTopic() == topic_id).toList();
        Collections.sort(result, Discussion::compareTo);
        return result;
    }

    public void createTopic(String title, String description, User user) {
        description = description == null ? "" : description;
        Topic topic = new Topic();
        topic.setTitle(title);
        topic.setDescription(description);
        topic.setAuthor(user.getUsername());
        topic.setLast_update(new Date());
        topic.setSize(0);
        topicRepo.save(topic);
    }

    public void createDiscussion(String title, long topic, User user) {
        Discussion discussion = new Discussion();
        discussion.setAuthor(user.getUsername());
        discussion.setDate(new Date());
        discussion.setTitle(title);
        discussion.setTopic(topic);
        discussion.setMessages(new ArrayList<>());
        discussion.setLast_update(new Date());
        discussion.setStatus(DiscussionStatus.OPEN);
        discussion.setPinned(false);
        discussionRepo.save(discussion);
    }

    public void editDiscussion(String title, long discussion_id, DiscussionStatus status, User user) {
        Discussion discussion = discussionRepo.findById(discussion_id).orElseThrow();
        if(!(discussion.getAuthor().equals(user.getUsername()) || user.isModer())) throw new SecurityException();
        discussion.setAuthor(user.getUsername());
        discussion.setTitle(title);
        discussion.setLast_update(new Date());
        discussion.setStatus(status);
        discussionRepo.save(discussion);
    }

    public void addDiscussionMessage(String text, long discussion_id, User user) {
        DiscussionMessage message = new DiscussionMessage();
        message.setDate(new Date());
        message.setEdited(false);
        message.setSender(user.getUsername());
        message.setText(text);
        message.setDiscussion_id(discussion_id);
        discussionMessageRepo.save(message);
        Discussion discussion = discussionRepo.findById(discussion_id).orElseThrow();
        List<Long> newMsgs = discussion.getMessages();
        newMsgs.add(message.getId());
        discussion.setMessages(newMsgs);
        discussionRepo.save(discussion);
    }

    public void editDiscussionMessage(String text, long message_id, User user) {
        DiscussionMessage message = discussionMessageRepo.findById(message_id).orElseThrow();
        if(!(message.getSender().equals(user.getUsername()) || user.isAdmin())) throw new SecurityException();
        message.setEdited(true);
        message.setText(text);
        discussionMessageRepo.save(message);
    }

    public void deleteDiscussionMessage(long message_id, User user) {
        DiscussionMessage message = discussionMessageRepo.findById(message_id).orElseThrow();
        if(!(message.getSender().equals(user.getUsername()) || user.isAdmin())) throw new SecurityException();
        discussionMessageRepo.delete(message);
    }

    public DiscussionMessage getDiscussionMessageById(long id) {
        return discussionMessageRepo.findById(id).orElseThrow();
    }
}
