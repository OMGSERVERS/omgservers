package com.omgservers.schema.model.matchmakerState;

import com.omgservers.schema.model.matchmaker.MatchmakerModel;
import com.omgservers.schema.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.schema.model.matchmakerMatchAssignment.MatchmakerMatchAssignmentModel;
import com.omgservers.schema.model.matchmakerMatchResource.MatchmakerMatchResourceModel;
import com.omgservers.schema.model.matchmakerRequest.MatchmakerRequestModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchmakerStateDto {

    @NotNull
    MatchmakerModel matchmaker;

    @NotNull
    List<MatchmakerCommandModel> matchmakerCommands;

    @NotNull
    List<MatchmakerRequestModel> matchmakerRequests;

    @NotNull
    List<MatchmakerMatchResourceModel> matchmakerMatchResources;

    @NotNull
    List<MatchmakerMatchAssignmentModel> matchmakerMatchAssignments;
}
