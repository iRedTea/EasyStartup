package site.easystartup.easystartupcore.project.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProjectDto implements Serializable {
    private final Long projectId;
    private final String coverLink;
    @Size(min = 3, max = 100, message = "Title should be no less 3 and no more 100 signs")
    @NotNull
    private final String title;
    @Size(min = 50, message = "Description should be no less 50 signs")
    @NotNull
    private final String description;
    private final int commercialStatus;
    @NotNull
    private final String technology;
    private final List<ParticipantDto> participants;
    private final LocalDateTime createdDate;
}
