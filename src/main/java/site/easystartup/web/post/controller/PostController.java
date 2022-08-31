package site.easystartup.web.post.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import site.easystartup.web.domain.model.User;
import site.easystartup.web.domain.response.MessageResponse;
import site.easystartup.web.domain.validation.ResponseErrorValidation;
import site.easystartup.web.post.domain.model.Post;
import site.easystartup.web.post.domain.request.PostRequest;
import site.easystartup.web.post.dto.PostDto;
import site.easystartup.web.post.service.PostService;
import site.easystartup.web.project.domain.requst.ProjectRequest;
import site.easystartup.web.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PostController {
    private final PostService postService;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final ResponseErrorValidation responseErrorValidation;

    @GetMapping("/")
    public ResponseEntity<List<PostDto>> main(Principal principal) {
        var posts = postService.getAllPostsByAuthor(principal.getName())
                .stream().map(post -> modelMapper.map(post, PostDto.class)).toList();
        return ResponseEntity.ok().body(posts);
    }

    @GetMapping("/new")
    public ResponseEntity<Object> createPost(@Valid @RequestBody PostRequest postRequest, BindingResult bindingResult,
                                             Principal principal) {
        var errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        Post post = postService.createPost(postRequest, principal);
        return ResponseEntity.ok().body(modelMapper.map(post, PostRequest.class));
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<PostDto>> user(@PathVariable String username) {
        var posts = postService.getAllPostsByAuthor(username)
                .stream().map(post -> modelMapper.map(post, PostDto.class)).toList();
        return ResponseEntity.ok().body(posts);
    }

    /*
    @PutMapping("/message/{message_id}/edit")
    public ResponseEntity<Object> messageEdit(@Valid @RequestBody PostRequest messageRequest,
                              @PathVariable long message_id,
                              BindingResult bindingResult,
                              Principal principal) {
        var errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        var messageUpdated = forumService.editDiscussionMessage(messageRequest, message_id, principal);
        return ResponseEntity.ok().body(modelMapper.map(messageUpdated, PostDto.class));
    }

    @DeleteMapping("/message/{message_id}/delete")
    public ResponseEntity<Object> messageDelete(@PathVariable long message_id, Principal principal) {
        Post message = forumService.getDiscussionMessageById(message_id);
        User user = userService.getUserByPrincipal(principal);
        if(!(message.getSender().equals(user.getUsername()) && user.isModer()))
            return ResponseEntity.ok(new MessageResponse("No permissions!"));
        forumService.deleteDiscussionMessage(message_id, user);
        return ResponseEntity.ok(new MessageResponse("Message was deleted!"));
    }

     */


}
