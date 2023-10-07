package com.omgservers.job.matchmaker.operation.handleMatchmakerCommands;

import com.omgservers.model.matchmakerChangeOfState.MatchmakerChangeOfState;
import com.omgservers.model.matchmakerState.IndexedMatchmakerState;
import io.smallrye.mutiny.Uni;

public interface HandleMatchmakerCommandsOperation {
    Uni<Void> handleMatchmakerCommands(Long matchmakerId,
                                       IndexedMatchmakerState indexedMatchmakerState,
                                       MatchmakerChangeOfState matchmakerChangeOfState);
}
