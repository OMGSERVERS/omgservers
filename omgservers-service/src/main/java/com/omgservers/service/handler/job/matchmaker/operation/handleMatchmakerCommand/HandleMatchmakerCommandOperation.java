package com.omgservers.service.handler.job.matchmaker.operation.handleMatchmakerCommand;

import com.omgservers.model.matchmakerChangeOfState.MatchmakerChangeOfState;
import com.omgservers.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.model.matchmakerState.MatchmakerStateModel;
import io.smallrye.mutiny.Uni;

public interface HandleMatchmakerCommandOperation {
    Uni<Void> handleMatchmakerCommand(MatchmakerStateModel matchmakerStateModel,
                                      MatchmakerChangeOfStateModel changeOfState,
                                      MatchmakerCommandModel matchmakerCommand);
}
