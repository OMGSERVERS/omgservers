package com.omgservers.schema.model.matchmakerChangeOfState;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.omgservers.schema.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.schema.model.matchmakerMatch.MatchmakerMatchModel;
import com.omgservers.schema.model.matchmakerMatchClient.MatchmakerMatchClientModel;
import com.omgservers.schema.model.request.MatchmakerRequestModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
public class MatchmakerChangeOfStateDto {

    @NotNull
    Set<MatchmakerCommandModel> commandsToDelete;

    @NotNull
    Set<MatchmakerRequestModel> requestsToDelete;

    @NotNull
    Set<MatchmakerMatchModel> matchesToSync;

    @NotNull
    Set<MatchmakerMatchModel> matchesToUpdateStatus;

    @NotNull
    Set<MatchmakerMatchModel> matchesToDelete;

    @NotNull
    Set<MatchmakerMatchClientModel> clientsToSync;

    @NotNull
    Set<MatchmakerMatchClientModel> clientsToDelete;

    public MatchmakerChangeOfStateDto() {
        commandsToDelete = new HashSet<>();
        requestsToDelete = new HashSet<>();
        matchesToSync = new HashSet<>();
        matchesToUpdateStatus = new HashSet<>();
        matchesToDelete = new HashSet<>();
        clientsToSync = new HashSet<>();
        clientsToDelete = new HashSet<>();
    }

    @JsonIgnore
    public boolean isNotEmpty() {
        return commandsToDelete.size() > 0 ||
                requestsToDelete.size() > 0 ||
                matchesToSync.size() > 0 ||
                matchesToUpdateStatus.size() > 0 ||
                matchesToDelete.size() > 0 ||
                clientsToSync.size() > 0 ||
                clientsToDelete.size() > 0;
    }
}
