package com.omgservers.service.job.matchmaker.operation.handlerMatchmakerRequests;

import com.omgservers.model.matchmakerChangeOfState.MatchmakerChangeOfState;
import com.omgservers.model.matchmakerState.MatchmakerState;
import io.smallrye.mutiny.Uni;

public interface HandleMatchmakerRequestsOperation {
    Uni<Void> handleMatchmakerRequests(Long matchmakerId,
                                       MatchmakerState matchmakerState,
                                       MatchmakerChangeOfState changeOfState);
}
