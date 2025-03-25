package com.omgservers.schema.model.matchmakerMatchAssignment;

import com.omgservers.schema.model.matchmakerRequest.MatchmakerRequestModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchmakerMatchAssignmentConfigDto {

    static public MatchmakerMatchAssignmentConfigDto create(final MatchmakerRequestModel request) {
        final var matchmakerMatchAssignmentConfig = new MatchmakerMatchAssignmentConfigDto();
        matchmakerMatchAssignmentConfig.setMatchmakerRequest(request);
        return matchmakerMatchAssignmentConfig;
    }

    @NotNull
    MatchmakerRequestModel matchmakerRequest;
}
