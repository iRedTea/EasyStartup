package site.easystartup.web.forum.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Data
public class TopicDto implements Serializable {
    private long id;
    @Size(min = 3, max = 100, message = "Title should be no less 3 and no more 100 signs")
    @NotNull
    private String title;
    private String author;
    private int size;
    private String description;
    private Date lastUpdate;
}
