package com.omgservers.service.handler.job.matchmaker.operation.handleEndedMatches;

import com.omgservers.model.matchmakerChangeOfState.MatchmakerChangeOfStateModel;
import com.omgservers.model.matchmakerState.MatchmakerStateModel;

public interface HandleEndedMatchesOperation {
    void handleEndedMatches(MatchmakerStateModel matchmakerStateModel,
                            MatchmakerChangeOfStateModel changeOfState);
}
