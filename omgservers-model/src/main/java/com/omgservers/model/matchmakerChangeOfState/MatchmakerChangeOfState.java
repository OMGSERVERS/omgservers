package com.omgservers.model.matchmakerChangeOfState;

import com.omgservers.model.match.MatchModel;
import com.omgservers.model.matchClient.MatchClientModel;
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
    Set<MatchmakerCommandModel> completedMatchmakerCommands;

    @NotNull
    Set<RequestModel> completedRequests;

    @NotNull
    Set<MatchModel> createdMatches;

    @NotNull
    Set<MatchModel> updatedMatches;

    @NotNull
    Set<MatchModel> endedMatches;

    @NotNull
    Set<MatchClientModel> createdMatchClients;

    @NotNull
    Set<MatchClientModel> orphanedMatchClients;

    public MatchmakerChangeOfState() {
        completedMatchmakerCommands = new HashSet<>();
        completedRequests = new HashSet<>();
        createdMatches = new HashSet<>();
        updatedMatches = new HashSet<>();
        endedMatches = new HashSet<>();
        createdMatchClients = new HashSet<>();
        orphanedMatchClients = new HashSet<>();
    }
}