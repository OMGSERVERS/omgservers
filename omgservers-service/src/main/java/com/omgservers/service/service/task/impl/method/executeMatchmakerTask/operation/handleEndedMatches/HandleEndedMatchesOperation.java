package com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation.handleEndedMatches;

import com.omgservers.schema.model.matchmakerChangeOfState.MatchmakerChangeOfStateModel;
import com.omgservers.schema.model.matchmakerState.MatchmakerStateModel;

public interface HandleEndedMatchesOperation {
    void handleEndedMatches(MatchmakerStateModel matchmakerStateModel,
                            MatchmakerChangeOfStateModel changeOfState);
}
