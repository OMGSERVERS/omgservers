package com.omgservers.job.matchmaker.operation.handleMatchmakerCommand;

import com.omgservers.model.matchmakerChangeOfState.MatchmakerChangeOfState;
import com.omgservers.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.model.matchmakerState.MatchmakerState;
import io.smallrye.mutiny.Uni;

public interface HandleMatchmakerCommandOperation {
    Uni<Void> handleMatchmakerCommand(MatchmakerState matchmakerState,
                                      MatchmakerChangeOfState changeOfState,
                                      MatchmakerCommandModel matchmakerCommand);
}
