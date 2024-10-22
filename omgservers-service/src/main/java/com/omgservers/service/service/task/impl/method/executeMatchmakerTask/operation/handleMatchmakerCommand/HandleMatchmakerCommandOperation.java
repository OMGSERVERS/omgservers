package com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation.handleMatchmakerCommand;

import com.omgservers.schema.model.matchmakerChangeOfState.MatchmakerChangeOfStateDto;
import com.omgservers.schema.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.schema.model.matchmakerState.MatchmakerStateDto;
import io.smallrye.mutiny.Uni;

public interface HandleMatchmakerCommandOperation {
    Uni<Void> handleMatchmakerCommand(MatchmakerStateDto matchmakerStateDto,
                                      MatchmakerChangeOfStateDto changeOfState,
                                      MatchmakerCommandModel matchmakerCommand);
}
