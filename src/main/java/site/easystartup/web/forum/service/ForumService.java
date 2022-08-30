package site.easystartup.web.forum.service;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import site.easystartup.web.domain.model.User;
import site.easystartup.web.exception.NoPermissionException;
import site.easystartup.web.forum.domain.exception.DiscussionMessageNotFoundException;
import site.easystartup.web.forum.domain.exception.DiscussionNotFoundException;
import site.easystartup.web.forum.domain.exception.TopicNotFoundException;
import site.easystartup.web.forum.domain.model.Discussion;
import site.easystartup.web.forum.domain.model.DiscussionMessage;
import site.easystartup.web.forum.domain.model.DiscussionStatus;
import site.easystartup.web.forum.domain.model.Topic;
import site.easystartup.web.forum.domain.request.DiscussionMessageRequest;
import site.easystartup.web.forum.domain.request.DiscussionRequest;
import site.easystartup.web.forum.domain.request.TopicRequest;
import site.easystartup.web.forum.dto.DiscussionDto;
import site.easystartup.web.forum.repo.DiscussionMessageRepo;
import site.easystartup.web.forum.repo.DiscussionRepo;
import site.easystartup.web.forum.repo.TopicRepo;
import site.easystartup.web.service.UserService;

import java.security.Principal;
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

    private final UserService userService;

    public List<Topic> getAllTopics() {
        return (List<Topic>) topicRepo.findAll();
    }

    public List<DiscussionMessage> getMessagesByDiscussionId(long discussionId) {
        return Lists.newArrayList(discussionMessageRepo.findAll()).stream()
                .filter(m -> m.getDiscussion_id() == discussionId).toList();
    }

    public Discussion getDiscussionById(long discussion_id) {
        return discussionRepo.findById(discussion_id)
                .orElseThrow(new DiscussionNotFoundException("Could not get discussion with id " + discussion_id));
    }

    public List<Discussion> getDiscussionsByAuthor(Principal principal) {
        return Lists.newArrayList(discussionRepo.findAll()).stream()
                .filter(d -> d.getAuthor().equals(principal.getName())).toList();
    }

    public List<Discussion> getDiscussionsByMember(Principal principal) {
         return Lists.newArrayList(discussionRepo.findAll()).stream()
                .filter(d -> getMessagesByDiscussionId(d.getId()).stream()
                        .anyMatch(m -> m.getSender().equals(principal.getName()))).toList();
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

    public List<Discussion> getDiscussionsByPage(long topic_id, long page) {
        List<Discussion> result = new ArrayList<>();
        if(page == 0) result.addAll(getPinnedDiscussionsByTopicId(topic_id));
        result.addAll(getSortedDiscussionsByTopicId(topic_id).subList((int)(15 * page), (int)(15 * page) + 15));
        return result;
    }

    public Topic createTopic(TopicRequest topicRequest, Principal principal) {
        String description = topicRequest.getDescription() == null ? "" : topicRequest.getDescription();
        Topic topic = new Topic();
        topic.setTitle(topic.getTitle());
        topic.setDescription(description);
        topic.setAuthor(principal.getName());
        topic.setLast_update(new Date());
        topic.setSize(0);
        topicRepo.save(topic);
        return topic;
    }

    public Topic getTopicById(long id) {
        return topicRepo.findById(id).orElseThrow(new TopicNotFoundException("Topic with id " + id + " dont exists!"));
    }

    public Discussion createDiscussion(DiscussionRequest discussionRequest, long topic_id, Principal principal) {
        Discussion discussion = new Discussion();
        discussion.setAuthor(principal.getName());
        discussion.setDate(new Date());
        discussion.setTitle(discussionRequest.getTitle());
        discussion.setTopic(topic_id);
        discussion.setMessages(new ArrayList<>());
        discussion.setLast_update(new Date());
        discussion.setStatus(DiscussionStatus.OPEN);
        discussion.setPinned(false);
        discussionRepo.save(discussion);
        onDiscussionUpdate(discussion);
        Topic topic = topicRepo.findById(topic_id)
                .orElseThrow(new TopicNotFoundException("Topic with id " + topic_id + " dont exists!"));
        topic.setSize(topic.getSize() + 1);
        topicRepo.save(topic);
        return discussion;
    }

    public Discussion editDiscussion(DiscussionRequest discussionRequest, long discussion_id, Principal principal) {
        User user = userService.getUserByPrincipal(principal);
        Discussion discussion = discussionRepo.findById(discussion_id)
                .orElseThrow(new DiscussionNotFoundException("Could not get discussion with id " + discussion_id));
        if(!(discussion.getAuthor().equals(user.getUsername()) || user.isModer()))
            throw new NoPermissionException(user.getUsername());
        discussion.setAuthor(user.getUsername());
        discussion.setTitle(discussionRequest.getTitle());
        discussion.setLast_update(new Date());
        discussion.setStatus(discussionRequest.getStatus());
        discussionRepo.save(discussion);
        onDiscussionUpdate(discussion);
        return discussion;
    }

    public DiscussionMessage createDiscussionMessage(DiscussionMessageRequest messageRequest, long discussion_id, Principal principal) {
        DiscussionMessage message = new DiscussionMessage();
        message.setDate(new Date());
        message.setEdited(false);
        message.setSender(principal.getName());
        message.setText(messageRequest.getText());
        message.setDiscussion_id(discussion_id);
        discussionMessageRepo.save(message);
        Discussion discussion = discussionRepo.findById(discussion_id)
                .orElseThrow(new DiscussionNotFoundException("Could not get discussion with id " + discussion_id));
        List<Long> newMsgs = discussion.getMessages();
        newMsgs.add(message.getId());
        discussion.setMessages(newMsgs);
        discussionRepo.save(discussion);
        onDiscussionMessageUpdate(message);
        return message;
    }

    public DiscussionMessage editDiscussionMessage(DiscussionMessageRequest messageRequest, long message_id, Principal principal) {
        User user = userService.getUserByPrincipal(principal);
        DiscussionMessage message = discussionMessageRepo.findById(message_id)
                .orElseThrow(new DiscussionMessageNotFoundException("Could not get message with id " + message_id));
        if(!(message.getSender().equals(user.getUsername()) || user.isAdmin()))
            throw new NoPermissionException(user.getUsername());
        message.setEdited(true);
        message.setText(messageRequest.getText());
        discussionMessageRepo.save(message);
        onDiscussionMessageUpdate(message);
        return message;
    }

    public void deleteDiscussionMessage(long message_id, User user) {
        DiscussionMessage message = discussionMessageRepo.findById(message_id)
                .orElseThrow(new DiscussionMessageNotFoundException("Could not get message with id " + message_id));
        if(!(message.getSender().equals(user.getUsername()) || user.isAdmin()))
            throw new NoPermissionException(user.getUsername());
        discussionMessageRepo.delete(message);
    }

    public DiscussionMessage getDiscussionMessageById(long id) {
        return discussionMessageRepo.findById(id)
                .orElseThrow(new DiscussionMessageNotFoundException("Could not get message with id " + id));
    }

    public void onDiscussionMessageUpdate(DiscussionMessage discussionMessage) {
        onDiscussionUpdate(getDiscussionById(discussionMessage.getDiscussion_id()));
    }

    public void onDiscussionUpdate(Discussion discussion) {
        discussion.setLast_update(new Date());
        discussionRepo.save(discussion);
        onTopicUpdate(getTopicById(discussion.getId()));
    }

    public void onTopicUpdate(Topic topic) {
        topic.setLast_update(new Date());
        topicRepo.save(topic);
    }
}
