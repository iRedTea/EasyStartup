package site.easystartup.web.forum.domain.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.easystartup.web.forum.domain.model.DiscussionStatus;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class DiscussionRequest {
    private long id;
    @Size(min = 3, max = 100, message = "Title should be no less 3 and no more 100 signs")
    @NotNull
    private String title;
    private String author;
    private Date date;
    private Date last_update;
    private boolean pinned;
    private DiscussionStatus status;
    @NotNull
    private long topic;
    private List<Long> messages;
}
