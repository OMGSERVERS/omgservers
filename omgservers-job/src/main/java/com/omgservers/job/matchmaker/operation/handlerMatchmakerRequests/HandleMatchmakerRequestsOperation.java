package com.omgservers.job.matchmaker.operation.handlerMatchmakerRequests;

import com.omgservers.model.matchmakerChangeOfState.MatchmakerChangeOfState;
import com.omgservers.model.matchmakerState.IndexedMatchmakerState;
import io.smallrye.mutiny.Uni;

public interface HandleMatchmakerRequestsOperation {
    Uni<Void> handleMatchmakerRequests(Long matchmakerId,
                                       IndexedMatchmakerState indexedMatchmakerState,
                                       MatchmakerChangeOfState matchmakerChangeOfState);
}
