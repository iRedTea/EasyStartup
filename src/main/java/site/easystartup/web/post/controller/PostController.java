package site.easystartup.web.post.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import site.easystartup.web.domain.response.MessageResponse;
import site.easystartup.web.domain.validation.ResponseErrorValidation;
import site.easystartup.web.exception.NoPermissionException;
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
    public ResponseEntity<List<PostDto>> main() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        var posts = postService.getAllPostsByAuthor(user.getUsername())
                .stream().map(post -> modelMapper.map(post, PostDto.class)).toList();
        return ResponseEntity.ok().body(posts);
    }

    @PostMapping("/new")
    public ResponseEntity<Object> createPost(@Valid @RequestBody PostRequest postRequest, BindingResult bindingResult) {
        var errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Post post = postService.createPost(postRequest, user.getUsername());
        return ResponseEntity.ok().body(modelMapper.map(post, PostDto.class));
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<PostDto>> user(@PathVariable String username) {
        var posts = postService.getAllPostsByAuthor(username)
                .stream().map(post -> modelMapper.map(post, PostDto.class)).toList();
        return ResponseEntity.ok().body(posts);
    }

    @PutMapping("/edit/{post_id}")
    public ResponseEntity<Object> edit(@Valid @RequestBody PostRequest postRequest, BindingResult bindingResult,
                                             @PathVariable long post_id) {
        var errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Post post = postService.edit(postRequest, post_id, user);
        return ResponseEntity.ok().body(modelMapper.map(post, PostDto.class));
    }

    @DeleteMapping("/delete/{post_id}/")
    public void delete(@PathVariable long post_id) {
        Post post = postService.getPostById(post_id);
        if(!post.getSender().equals(userService.getCurrentUsername()) && !userService.getCurrentUser().isAdmin())
            throw new NoPermissionException(String.format("User %s cant delete post %s", userService.getCurrentUsername(), post_id));
        else postService.delete(post);
    }

    @PutMapping("/like/{post_id}/")
    public ResponseEntity<Object> like(@PathVariable long post_id) {
        Post post = postService.getPostById(post_id);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Post edited = postService.addLike(post, user);
        return ResponseEntity.ok().body(modelMapper.map(edited, PostRequest.class));
    }

    @PutMapping("/dislike/{post_id}/")
    public ResponseEntity<Object> dislike(@PathVariable long post_id) {
        Post post = postService.getPostById(post_id);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Post edited = postService.disLike(post, user);
        return ResponseEntity.ok().body(modelMapper.map(edited, PostRequest.class));
    }


}
