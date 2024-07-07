package site.easystartup.web.project.domain.requst;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ParticipantRequest {
    private Long projectId;
    private Long participantId;
    private String nameOfPosition;
    private boolean approved;
}
