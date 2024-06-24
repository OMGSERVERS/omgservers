package com.omgservers.model.matchmakerState;

import com.omgservers.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.model.matchmakerMatch.MatchmakerMatchModel;
import com.omgservers.model.matchmakerMatchClient.MatchmakerMatchClientModel;
import com.omgservers.model.request.MatchmakerRequestModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchmakerStateModel {

    @NotNull
    List<MatchmakerCommandModel> commands;

    @NotNull
    List<MatchmakerRequestModel> requests;

    @NotNull
    List<MatchmakerMatchModel> matches;

    @NotNull
    List<MatchmakerMatchClientModel> clients;
}
