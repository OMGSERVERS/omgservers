package com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation.handleClosedMatches;

import com.omgservers.schema.model.matchmakerChangeOfState.MatchmakerChangeOfStateDto;
import com.omgservers.schema.model.matchmakerState.MatchmakerStateDto;

public interface HandleClosedMatchesOperation {
    void execute(MatchmakerStateDto matchmakerState,
                 MatchmakerChangeOfStateDto matchmakerChangeOfState);
}
