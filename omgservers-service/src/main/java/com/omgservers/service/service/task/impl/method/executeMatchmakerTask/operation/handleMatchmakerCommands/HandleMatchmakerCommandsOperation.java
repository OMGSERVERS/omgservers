package com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation.handleMatchmakerCommands;

import com.omgservers.schema.model.matchmakerChangeOfState.MatchmakerChangeOfStateDto;
import com.omgservers.schema.model.matchmakerState.MatchmakerStateDto;

public interface HandleMatchmakerCommandsOperation {
    void execute(MatchmakerStateDto matchmakerState,
                 MatchmakerChangeOfStateDto matchmakerChangeOfState);
}
