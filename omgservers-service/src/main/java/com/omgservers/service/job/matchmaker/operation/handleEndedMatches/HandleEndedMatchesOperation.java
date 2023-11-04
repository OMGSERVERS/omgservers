package com.omgservers.service.job.matchmaker.operation.handleEndedMatches;

import com.omgservers.model.matchmakerChangeOfState.MatchmakerChangeOfState;
import com.omgservers.model.matchmakerState.MatchmakerState;

public interface HandleEndedMatchesOperation {
    void handleEndedMatches(MatchmakerState matchmakerState,
                            MatchmakerChangeOfState changeOfState);
}
