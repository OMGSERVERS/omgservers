package com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation.handleEmptyMatchesOperation;

import com.omgservers.schema.model.matchmakerChangeOfState.MatchmakerChangeOfStateDto;
import com.omgservers.schema.model.matchmakerState.MatchmakerStateDto;

public interface HandleEmptyMatchesOperation {
    void execute(MatchmakerStateDto matchmakerState,
                 MatchmakerChangeOfStateDto matchmakerChangeOfState);
}
