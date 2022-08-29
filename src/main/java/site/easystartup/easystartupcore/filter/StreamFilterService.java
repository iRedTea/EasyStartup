package site.easystartup.easystartupcore.filter;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import site.easystartup.easystartupcore.domain.forum.Discussion;
import site.easystartup.easystartupcore.domain.forum.DiscussionMessage;
import site.easystartup.easystartupcore.repos.forum.DiscussionMessageRepo;

import java.util.List;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class StreamFilterService implements FilterService {
    private final DiscussionMessageRepo discussionMessageRepo;

    @Override
    public List<DiscussionMessage> getMessagesByDiscussionId(long discussionId) {
        //Lists.newArrayList(discussionMessageRepo.findAll()).stream().filter()
        return null;
    }

    @Override
    public List<Discussion> getDiscussionByAuthor(String author) {
        return null;
    }

    @Override
    public List<Discussion> getDiscussionByMember(String member) {
        return null;
    }
}
