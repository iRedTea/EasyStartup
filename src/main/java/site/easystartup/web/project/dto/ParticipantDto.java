package site.easystartup.web.project.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Data
@NoArgsConstructor
public class ParticipantDto implements Serializable {
    private  Long participantId;
    @NotNull
    private  String nameOfPosition;
    private  UserDto user;
    private  Set<UserDto> requests;
}
