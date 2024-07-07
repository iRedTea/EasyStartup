package site.easystartup.web.project.domain.requst;

import lombok.Data;
import lombok.NoArgsConstructor;
import site.easystartup.web.project.domain.model.Technology;
import site.easystartup.web.project.dto.ParticipantDto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
public class ProjectRequest {

    //    private MultipartFile cover;

    @Size(min = 3, max = 100, message = "Title should be no less 3 and no more 100 signs")
    @NotNull
    private String title;
    @Size(min = 50, message = "Description should be no less 50 signs")
    @NotNull
    private String description;
    private int commercialStatus;
    @NotNull
    private List<Technology> technology;
    private List<ParticipantDto> participants;
}
