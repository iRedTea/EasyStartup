package site.easystartup.web.project.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Data
public class ParticipantDto implements Serializable {
    private final Long participantId;
    @NotNull
    private final String technology;
    private final String nameOfPosition;
    private final UserDto user;
    private final Set<UserDto> requests;
}
