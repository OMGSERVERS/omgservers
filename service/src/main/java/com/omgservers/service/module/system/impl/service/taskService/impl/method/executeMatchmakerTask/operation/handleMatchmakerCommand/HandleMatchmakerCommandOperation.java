package com.omgservers.service.module.system.impl.service.taskService.impl.method.executeMatchmakerTask.operation.handleMatchmakerCommand;

import com.omgservers.model.matchmakerChangeOfState.MatchmakerChangeOfStateModel;
import com.omgservers.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.model.matchmakerState.MatchmakerStateModel;
import io.smallrye.mutiny.Uni;

public interface HandleMatchmakerCommandOperation {
    Uni<Void> handleMatchmakerCommand(MatchmakerStateModel matchmakerStateModel,
                                      MatchmakerChangeOfStateModel changeOfState,
                                      MatchmakerCommandModel matchmakerCommand);
}
