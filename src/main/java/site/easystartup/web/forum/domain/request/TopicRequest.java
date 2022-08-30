package site.easystartup.web.forum.domain.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Builder
public class TopicRequest {
    private long id;
    @Size(min = 3, max = 100, message = "Title should be no less 3 and no more 100 signs")
    @NotNull
    private String title;
    @NotNull
    private String author;
    private int size;
    private String description;
    @NotNull
    private Date last_update;
}
