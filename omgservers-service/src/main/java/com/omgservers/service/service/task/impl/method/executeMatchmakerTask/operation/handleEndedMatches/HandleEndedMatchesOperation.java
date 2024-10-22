package com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation.handleEndedMatches;

import com.omgservers.schema.model.matchmakerChangeOfState.MatchmakerChangeOfStateDto;
import com.omgservers.schema.model.matchmakerState.MatchmakerStateDto;

public interface HandleEndedMatchesOperation {
    void handleEndedMatches(MatchmakerStateDto matchmakerStateDto,
                            MatchmakerChangeOfStateDto changeOfState);
}
