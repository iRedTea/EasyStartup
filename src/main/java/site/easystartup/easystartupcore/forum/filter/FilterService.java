package site.easystartup.easystartupcore.forum.filter;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import site.easystartup.easystartupcore.forum.domain.Discussion;
import site.easystartup.easystartupcore.forum.domain.DiscussionMessage;
import site.easystartup.easystartupcore.forum.repo.DiscussionMessageRepo;
import site.easystartup.easystartupcore.forum.repo.DiscussionRepo;

import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilterService {
    private final DiscussionMessageRepo discussionMessageRepo;

    private final DiscussionRepo discussionRepo;

    public List<DiscussionMessage> getMessagesByDiscussionId(long discussionId) {
        return Lists.newArrayList(discussionMessageRepo.findAll()).stream()
                .filter(m -> m.getId() == discussionId).toList();
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
}
