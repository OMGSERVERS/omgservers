package com.omgservers.model.matchmakerChangeOfState;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.omgservers.model.matchmakerMatch.MatchmakerMatchModel;
import com.omgservers.model.matchmakerMatchClient.MatchmakerMatchClientModel;
import com.omgservers.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.model.request.MatchmakerRequestModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
public class MatchmakerChangeOfState {

    @NotNull
    Set<MatchmakerCommandModel> commandsToDelete;

    @NotNull
    Set<MatchmakerRequestModel> completedRequests;

    @NotNull
    Set<MatchmakerMatchModel> createdMatches;

    @NotNull
    Set<MatchmakerMatchModel> stoppedMatches;

    @NotNull
    Set<MatchmakerMatchModel> endedMatches;

    @NotNull
    Set<MatchmakerMatchClientModel> clientsToSync;

    @NotNull
    Set<MatchmakerMatchClientModel> clientsToDelete;

    public MatchmakerChangeOfState() {
        completedMatchmakerCommands = new HashSet<>();
        completedRequests = new HashSet<>();
        createdMatches = new HashSet<>();
        stoppedMatches = new HashSet<>();
        endedMatches = new HashSet<>();
        createdMatchClients = new HashSet<>();
        orphanedMatchClients = new HashSet<>();
    }

    @JsonIgnore
    public boolean isNotEmpty() {
        return completedMatchmakerCommands.size() > 0 ||
                completedRequests.size() > 0 ||
                createdMatches.size() > 0 ||
                stoppedMatches.size() > 0 ||
                endedMatches.size() > 0 ||
                createdMatchClients.size() > 0 ||
                orphanedMatchClients.size() > 0;
    }
}
