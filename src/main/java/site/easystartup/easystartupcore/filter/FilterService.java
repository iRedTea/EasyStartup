package site.easystartup.easystartupcore.filter;

import site.easystartup.easystartupcore.domain.forum.Discussion;
import site.easystartup.easystartupcore.domain.forum.DiscussionMessage;

import java.util.List;

public interface FilterService {
    List<DiscussionMessage> getMessagesByDiscussionId(long discussionId);

    List<Discussion> getDiscussionByAuthor(String author);

    List<Discussion> getDiscussionByMember(String member);
}
