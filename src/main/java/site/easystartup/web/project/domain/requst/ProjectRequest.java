package site.easystartup.web.project.domain.requst;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import site.easystartup.web.project.domain.model.Tag;
import site.easystartup.web.project.dto.ParticipantDto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@RequiredArgsConstructor
public class ProjectRequest {

    private MultipartFile cover;

    @Size(min = 3, max = 100, message = "Title should be no less 3 and no more 100 signs")
    @NotNull
    private String title;
    @Size(min = 50, message = "Description should be no less 50 signs")
    @NotNull
    private String description;
    private int commercialStatus;
    @NotNull
    private List<Tag> technology;
    private List<ParticipantDto> participants;
}
