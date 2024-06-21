package com.omgservers.service.module.system.impl.service.taskService.impl.method.executeMatchmakerTask.operation.handleEndedMatches;

import com.omgservers.model.matchmakerChangeOfState.MatchmakerChangeOfStateModel;
import com.omgservers.model.matchmakerState.MatchmakerStateModel;

public interface HandleEndedMatchesOperation {
    void handleEndedMatches(MatchmakerStateModel matchmakerStateModel,
                            MatchmakerChangeOfStateModel changeOfState);
}
