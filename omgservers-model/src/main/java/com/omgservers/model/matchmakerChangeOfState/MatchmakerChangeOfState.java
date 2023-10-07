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
    Set<MatchmakerCommandModel> deletedMatchmakerCommands;

    @NotNull
    Set<MatchModel> createdMatches;

    @NotNull
    Set<MatchModel> updatedMatches;

    @NotNull
    Set<MatchModel> deletedMatches;

    @NotNull
    Set<MatchClientModel> createdMatchClients;

    @NotNull
    Set<MatchClientModel> deletedMatchClients;

    @NotNull
    Set<RequestModel> deletedRequests;

    public MatchmakerChangeOfState() {
        deletedMatchmakerCommands = new HashSet<>();
        createdMatches = new HashSet<>();
        updatedMatches = new HashSet<>();
        deletedMatches = new HashSet<>();
        createdMatchClients = new HashSet<>();
        deletedMatchClients = new HashSet<>();
        deletedRequests = new HashSet<>();
    }
}
