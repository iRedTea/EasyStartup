package site.easystartup.web.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import site.easystartup.web.post.domain.event.RepostEvent;
import site.easystartup.web.post.domain.exception.AlreadyLikedException;
import site.easystartup.web.post.domain.exception.PostNotFoundException;
import site.easystartup.web.post.domain.model.Post;
import site.easystartup.web.post.domain.request.PostRequest;
import site.easystartup.web.post.repo.PostRepo;

import java.security.Principal;
import java.util.ArrayList;
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
        checkAnswers(post);
        return postRepo.save(post);
    }

    public Post edit(PostRequest postRequest, long post_id, Principal principal) {
        Post post = postRepo.findById(post_id).orElseThrow(
                new PostNotFoundException("Post with id " + post_id + " didnt exists"));
        post.setEdited(true);
        post.setSender(principal.getName());
        post.setText(postRequest.getText());
        checkAnswers(post);
        return postRepo.save(post);
    }

    public void delete(Post post) {
        postRepo.delete(post);
    }

    public void checkAnswers(Post post) {
        if(post.getAnswered_post() != null) {
            Post answered = postRepo.findById(post.getAnswered_post()).orElseThrow(
                    new PostNotFoundException("Post didnt exists: " + post.getAnswered_post()));
            List<Long> answers = answered.getAnswers() != null ? answered.getAnswers() : new ArrayList<>();
            answers.add(post.getId());
            answered.setAnswers(answers);
            new RepostEvent(answered.getSender(),
                    post.getSender(),
                    String.format("Пользователь %s ответил на вашу запись!", answered.getSender())).invoke();
            postRepo.save(answered);
        }
    }

    public Post addLike(Post post, Principal principal) {
        if (post.getLiked_users().contains(principal.getName()))
            throw new AlreadyLikedException(String.format("User %s already liked post %s", principal.getName(), post.getId()));
        post.setLike(post.getLike() + 1);
        List<String> liked = post.getLiked_users() == null ? new ArrayList<>() : post.getLiked_users();
        liked.add(principal.getName());
        return postRepo.save(post);
    }

    public Post disLike(Post post, Principal principal) {
        if (!post.getLiked_users().contains(principal.getName()))
            throw new AlreadyLikedException(String.format("User %s already liked post %s", principal.getName(), post.getId()));
        post.setLike(post.getLike() - 1);
        List<String> liked = post.getLiked_users() == null ? new ArrayList<>() : post.getLiked_users();
        liked.remove(principal.getName());
        return postRepo.save(post);
    }
}
