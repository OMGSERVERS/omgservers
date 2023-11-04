package com.omgservers.model.matchmakerState;

import com.omgservers.model.match.MatchModel;
import com.omgservers.model.matchClient.MatchClientModel;
import com.omgservers.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.model.request.RequestModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MatchmakerState {

    @NotNull
    List<MatchmakerCommandModel> matchmakerCommands;

    @NotNull
    List<RequestModel> requests;

    @NotNull
    List<MatchModel> matches;

    @NotNull
    List<MatchClientModel> matchClients;
}
