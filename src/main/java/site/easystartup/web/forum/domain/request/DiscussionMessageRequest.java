package site.easystartup.web.forum.domain.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Builder
public class DiscussionMessageRequest {
    private long id;
    @Size(min = 3, max = 1024, message = "Text should be no less 3 and no more 1024 signs")
    @NotNull
    private String text;
    @NotNull
    private String sender;
    @NotNull
    private Date date;
    private boolean edited;
    private long discussion_id;
}
