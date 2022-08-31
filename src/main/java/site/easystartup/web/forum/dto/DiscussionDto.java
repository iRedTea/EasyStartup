package site.easystartup.web.forum.dto;

import lombok.Data;
import site.easystartup.web.forum.domain.model.DiscussionStatus;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class DiscussionDto implements Serializable {
    private long id;
    @Size(min = 3, max = 100, message = "Title should be no less 3 and no more 100 signs")
    @NotNull
    private String title;
    private String author;
    private Date date;
    private Date last_update;
    private boolean pinned;
    private DiscussionStatus status;
    private long topic;
    private List<Long> messages;

}
