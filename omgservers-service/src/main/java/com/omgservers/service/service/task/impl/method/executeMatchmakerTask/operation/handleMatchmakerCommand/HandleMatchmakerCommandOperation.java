package com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation.handleMatchmakerCommand;

import com.omgservers.schema.model.matchmakerChangeOfState.MatchmakerChangeOfStateModel;
import com.omgservers.schema.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.schema.model.matchmakerState.MatchmakerStateModel;
import io.smallrye.mutiny.Uni;

public interface HandleMatchmakerCommandOperation {
    Uni<Void> handleMatchmakerCommand(MatchmakerStateModel matchmakerStateModel,
                                      MatchmakerChangeOfStateModel changeOfState,
                                      MatchmakerCommandModel matchmakerCommand);
}
