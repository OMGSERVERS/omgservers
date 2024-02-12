package com.omgservers.service.module.system.impl.service.handlerService.impl.method.handleEvents.handler.job.task.matchmaker.operation.handlerMatchmakerRequests;

import com.omgservers.model.matchmakerChangeOfState.MatchmakerChangeOfState;
import com.omgservers.model.matchmakerState.MatchmakerState;
import io.smallrye.mutiny.Uni;

public interface HandleMatchmakerRequestsOperation {
    Uni<Void> handleMatchmakerRequests(Long matchmakerId,
                                       MatchmakerState matchmakerState,
                                       MatchmakerChangeOfState changeOfState);
}
