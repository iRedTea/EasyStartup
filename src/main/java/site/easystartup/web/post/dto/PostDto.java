package site.easystartup.web.post.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class PostDto implements Serializable {
    private long id;
    @Size(min = 3, max = 1024, message = "Text should be no less 3 and no more 1024 signs")
    @NotNull
    private String text;
    private String sender;
    private Date date;
    private boolean edited;
    private long discussion_id;
    private long like;
    private List<Long> answers;
    private long answered_post;
}
