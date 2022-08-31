package site.easystartup.web.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import site.easystartup.web.post.domain.exception.PostNotFoundException;
import site.easystartup.web.post.domain.model.Post;
import site.easystartup.web.post.domain.request.PostRequest;
import site.easystartup.web.post.repo.PostRepo;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PostService {
    private final PostRepo postRepo;

    public Post getPostById(long id) {
        return postRepo.findById(id).orElseThrow(new PostNotFoundException("Post with id " + id + " dont exists!"));
    }

    public List<Post> getAllPostsByAuthor(String username) {
        return postRepo.findAll().stream().filter(post -> post.getSender().equals(username)).toList();
    }

    public Post createPost(PostRequest postRequest, Principal principal) {
        Post post = new Post();
        post.setAnswered_post(post.getAnswered_post());
        post.setAnswers(post.getAnswers());
        post.setDate(new Date());
        post.setEdited(false);
        post.setLike(0);
        post.setSender(principal.getName());
        post.setText(postRequest.getText());
        return postRepo.save(post);
    }
}
