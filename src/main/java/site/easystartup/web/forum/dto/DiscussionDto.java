package site.easystartup.web.forum.dto;

import lombok.Data;
import site.easystartup.web.forum.domain.model.DiscussionStatus;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
public class DiscussionDto {
    private long id;
    @Size(min = 3, max = 100, message = "Title should be no less 3 and no more 100 signs")
    @NotNull
    private String title;
    @NotNull
    private String author;
    @NotNull
    private Date date;
    @NotNull
    private Date last_update;
    private boolean pinned;
    @NotNull
    private DiscussionStatus status;
    private long topic;
    @NotNull
    private List<Long> messages;

}
