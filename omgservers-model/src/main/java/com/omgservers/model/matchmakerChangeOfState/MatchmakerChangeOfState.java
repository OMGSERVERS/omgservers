package com.omgservers.model.matchmakerChangeOfState;

import com.omgservers.model.match.MatchModel;
import com.omgservers.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.model.request.RequestModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
public class MatchmakerChangeOfState {

    @NotNull
    Set<MatchmakerCommandModel> deletedCommands;

    @NotNull
    Set<RequestModel> deletedRequests;

    @NotNull
    Set<MatchModel> createdMatches;

    @NotNull
    Set<MatchModel> updatedMatches;

    public MatchmakerChangeOfState() {
        deletedCommands = new HashSet<>();
        deletedRequests = new HashSet<>();
        createdMatches = new HashSet<>();
        updatedMatches = new HashSet<>();
    }
}
