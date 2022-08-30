package site.easystartup.web.forum.controller;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import site.easystartup.web.domain.model.User;
import site.easystartup.web.domain.response.ResponseMessage;
import site.easystartup.web.domain.validation.ResponseErrorValidation;
import site.easystartup.web.forum.domain.model.Discussion;
import site.easystartup.web.forum.domain.model.DiscussionMessage;
import site.easystartup.web.forum.domain.model.DiscussionStatus;
import site.easystartup.web.forum.domain.model.Topic;
import site.easystartup.web.forum.domain.request.DiscussionMessageRequest;
import site.easystartup.web.forum.domain.request.DiscussionRequest;
import site.easystartup.web.forum.domain.request.TopicRequest;
import site.easystartup.web.forum.dto.DiscussionDto;
import site.easystartup.web.forum.dto.DiscussionMessageDto;
import site.easystartup.web.forum.dto.TopicDto;
import site.easystartup.web.forum.service.ForumService;
import site.easystartup.web.forum.repo.TopicRepo;
import site.easystartup.web.project.domain.model.Project;
import site.easystartup.web.project.domain.requst.ProjectRequest;
import site.easystartup.web.project.dto.ProjectDto;
import site.easystartup.web.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/forum")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ForumController {
    private final ForumService forumService;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final ResponseErrorValidation responseErrorValidation;

    @GetMapping("/")
    public ResponseEntity<List<TopicDto>> main(@Valid @RequestBody ProjectRequest projectRequest,
                                                    BindingResult bindingResult,
                                                    Principal principal) {
        val topics = forumService.getAllTopics()
                .stream().map(topic -> modelMapper.map(topic, TopicDto.class)).collect(Collectors.toList());
        return ResponseEntity.ok().body(topics);
    }

    @GetMapping("/my")
    public ResponseEntity<List<DiscussionDto>> my(Principal principal) {
        val discussions = forumService.getDiscussionsByAuthor(principal)
                .stream().map(discussion -> modelMapper.map(discussion, DiscussionDto.class)).toList();
        return ResponseEntity.ok().body(discussions);
    }

    @GetMapping("/takepart")
    public ResponseEntity<List<DiscussionDto>> takePart(Principal principal) {
        val discussions = forumService.getDiscussionsByMember(principal)
                .stream().map(discussion -> modelMapper.map(discussion, DiscussionDto.class)).toList();
        return ResponseEntity.ok().body(discussions);
    }

    @Secured({"ADMIN", "MODER"})
    @PostMapping("/new")
    public ResponseEntity<Object> forumNew(@Valid @RequestBody TopicRequest topicRequest,
                                           BindingResult bindingResult,
                                           Principal principal) {
        val errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        Topic topic = forumService.createTopic(topicRequest, principal);
        return ResponseEntity.ok().body(modelMapper.map(topic, TopicDto.class));
    }

    @GetMapping("/{topic_id}/{page_id}")
    public ResponseEntity<List<DiscussionDto>> topicPage(@PathVariable long topic_id,
                              @PathVariable long page_id) {
        List<DiscussionDto> discussions = forumService.getDiscussionsByPage(topic_id, page_id)
                .stream().map(discussion -> modelMapper.map(discussion, DiscussionDto.class)).toList();
        return ResponseEntity.ok().body(discussions);
    }

    @GetMapping("/discussion/{discussion_id}")
    public ResponseEntity<DiscussionDto> discussion(@PathVariable(value = "discussion_id") long discussion_id) {
        return ResponseEntity.ok().body(modelMapper.map(forumService.getDiscussionById(discussion_id), DiscussionDto.class));
    }

    @PostMapping("/{topic_id}/new")
    public ResponseEntity<Object> discussionNew(@PathVariable long topic_id,
                                   @Valid @RequestBody DiscussionRequest discussionRequest,
                                   BindingResult bindingResult,
                                   Principal principal) {
        val errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        val discussion = forumService.createDiscussion(discussionRequest, topic_id, principal);
        return ResponseEntity.ok().body(modelMapper.map(discussion, DiscussionDto.class));
    }

    @PutMapping("/discussion/{discussion_id}/edit")
    public ResponseEntity<Object> discussionEdit(@Valid @RequestBody DiscussionRequest discussionRequest,
                                 @PathVariable long discussion_id,
                                 BindingResult bindingResult,
                                 Principal principal) {
        val errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        val discussionUpdated = forumService.editDiscussion(discussionRequest, discussion_id, principal);
        return ResponseEntity.ok().body(modelMapper.map(discussionUpdated, DiscussionDto.class));
    }

    @PostMapping("/discussion/{discussion_id}/new")
    public ResponseEntity<Object> messageAdd(@PathVariable long discussion_id,
                                             @Valid @RequestBody DiscussionMessageRequest messageRequest,
                                             BindingResult bindingResult,
                                             Principal principal) {
        val errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        val discussionMessage = forumService.createDiscussionMessage(messageRequest, discussion_id, principal);
        return ResponseEntity.ok().body(modelMapper.map(discussionMessage, DiscussionMessageDto.class));
    }

    @GetMapping("/message/{message_id}")
    public ResponseEntity<DiscussionMessageDto> message(@PathVariable long message_id) {
        return ResponseEntity.ok().body(modelMapper.map(forumService.getDiscussionMessageById(message_id), DiscussionMessageDto.class));
    }

    @PutMapping("/message/{message_id}/edit")
    public ResponseEntity<Object> messageEdit(@Valid @RequestBody DiscussionMessageRequest messageRequest,
                              @PathVariable long message_id,
                              BindingResult bindingResult,
                              Principal principal) {
        val errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        val messageUpdated = forumService.editDiscussionMessage(messageRequest, message_id, principal);
        return ResponseEntity.ok().body(modelMapper.map(messageUpdated, DiscussionMessageDto.class));
    }

    @DeleteMapping("/forum/message/{message_id}/delete")
    public ResponseEntity<Object> messageDelete(@PathVariable long message_id, Principal principal) {
        DiscussionMessage message = forumService.getDiscussionMessageById(message_id);
        User user = userService.getUserByPrincipal(principal);
        if(!(message.getSender().equals(user.getUsername()) || user.isModer()))
            return ResponseEntity.ok(new ResponseMessage("No permissions!"));
        forumService.deleteDiscussionMessage(message_id, user);
        return ResponseEntity.ok(new ResponseMessage("Message was deleted!"));
    }


}
