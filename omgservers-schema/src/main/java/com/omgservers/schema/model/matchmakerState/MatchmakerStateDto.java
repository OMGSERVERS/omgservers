package com.omgservers.schema.model.matchmakerState;

import com.omgservers.schema.model.matchmaker.MatchmakerModel;
import com.omgservers.schema.model.matchmakerAssignment.MatchmakerAssignmentModel;
import com.omgservers.schema.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.schema.model.matchmakerMatch.MatchmakerMatchModel;
import com.omgservers.schema.model.matchmakerMatchAssignment.MatchmakerMatchAssignmentModel;
import com.omgservers.schema.model.request.MatchmakerRequestModel;
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
    List<MatchmakerAssignmentModel> matchmakerAssignments;

    @NotNull
    List<MatchmakerCommandModel> matchmakerCommands;

    @NotNull
    List<MatchmakerRequestModel> matchmakerRequests;

    @NotNull
    List<MatchmakerMatchModel> matchmakerMatches;

    @NotNull
    List<MatchmakerMatchAssignmentModel> matchmakerMatchAssignments;
}
