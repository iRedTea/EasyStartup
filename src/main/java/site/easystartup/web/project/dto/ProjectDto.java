package site.easystartup.web.project.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class ProjectDto implements Serializable {
    private  Long projectId;
    private  String coverLink;
    @Size(min = 3, max = 100, message = "Title should be no less 3 and no more 100 signs")
    @NotNull
    private  String title;
    @Size(min = 50, message = "Description should be no less 50 signs")
    @NotNull
    private  String description;
    private  int commercialStatus;
    @NotNull
    private  String technology;
    private  List<ParticipantDto> participants;
    private  LocalDateTime createdDate;
}
