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

    //@
    @GetMapping("/")
    public ResponseEntity<List<PostDto>> main(Principal principal) {
        var posts = postService.getAllPostsByAuthor(principal.getName())
                .stream().map(post -> modelMapper.map(post, PostDto.class)).toList();
        return ResponseEntity.ok().body(posts);
    }

    //@
    @PostMapping("/new")
    public ResponseEntity<Object> createPost(@Valid @RequestBody PostRequest postRequest, BindingResult bindingResult,
                                             Principal principal) {
        var errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        Post post = postService.createPost(postRequest, principal);
        return ResponseEntity.ok().body(modelMapper.map(post, PostDto.class));
    }

    //@
    @GetMapping("/user/{username}")
    public ResponseEntity<List<PostDto>> user(@PathVariable String username) {
        var posts = postService.getAllPostsByAuthor(username)
                .stream().map(post -> modelMapper.map(post, PostDto.class)).toList();
        return ResponseEntity.ok().body(posts);
    }

    //@
    @PutMapping("/edit/{post_id}")
    public ResponseEntity<Object> edit(@Valid @RequestBody PostRequest postRequest, BindingResult bindingResult,
                                             Principal principal, @PathVariable long post_id) {
        var errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        Post post = postService.edit(postRequest, post_id, principal);
        return ResponseEntity.ok().body(modelMapper.map(post, PostDto.class));
    }

    //@
    @DeleteMapping("/delete/{post_id}/")
    public void delete(@PathVariable long post_id,
                       Principal principal) {
        Post post = postService.getPostById(post_id);
        if(!post.getSender().equals(principal.getName()) && !userService.getUserByPrincipal(principal).isAdmin())
            throw new NoPermissionException(String.format("User %s cant delete post %s", principal.getName(), post_id));
        else postService.delete(post);
    }

    //@
    @PutMapping("/like/{post_id}/")
    public ResponseEntity<Object> like(Principal principal, @PathVariable long post_id) {
        Post post = postService.getPostById(post_id);

        Post edited = postService.addLike(post, principal);
        return ResponseEntity.ok().body(modelMapper.map(edited, PostRequest.class));
    }

    //@
    @PutMapping("/dislike/{post_id}/")
    public ResponseEntity<Object> dislike(Principal principal, @PathVariable long post_id) {
        Post post = postService.getPostById(post_id);

        Post edited = postService.disLike(post, principal);
        return ResponseEntity.ok().body(modelMapper.map(edited, PostRequest.class));
    }


}
