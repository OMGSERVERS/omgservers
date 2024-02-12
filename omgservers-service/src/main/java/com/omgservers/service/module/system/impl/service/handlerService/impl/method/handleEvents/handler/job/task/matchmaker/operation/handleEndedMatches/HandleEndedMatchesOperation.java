package com.omgservers.service.module.system.impl.service.handlerService.impl.method.handleEvents.handler.job.task.matchmaker.operation.handleEndedMatches;

import com.omgservers.model.matchmakerChangeOfState.MatchmakerChangeOfState;
import com.omgservers.model.matchmakerState.MatchmakerState;

public interface HandleEndedMatchesOperation {
    void handleEndedMatches(MatchmakerState matchmakerState,
                            MatchmakerChangeOfState changeOfState);
}
