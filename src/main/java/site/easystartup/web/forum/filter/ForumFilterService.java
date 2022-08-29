package site.easystartup.web.forum.filter;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import site.easystartup.web.forum.domain.Discussion;
import site.easystartup.web.forum.domain.DiscussionMessage;
import site.easystartup.web.forum.repo.DiscussionMessageRepo;
import site.easystartup.web.forum.repo.DiscussionRepo;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ForumFilterService {
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
}
