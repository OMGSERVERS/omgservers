package com.omgservers.service.module.system.impl.service.taskService.impl.method.executeMatchmakerTask.operation.handlerMatchmakerRequests;

import com.omgservers.model.matchmaker.MatchmakerModel;
import com.omgservers.model.matchmakerChangeOfState.MatchmakerChangeOfStateModel;
import com.omgservers.model.matchmakerState.MatchmakerStateModel;
import io.smallrye.mutiny.Uni;

public interface HandleMatchmakerRequestsOperation {
    Uni<Void> handleMatchmakerRequests(MatchmakerModel matchmaker,
                                       MatchmakerStateModel currentState,
                                       MatchmakerChangeOfStateModel changeOfState);
}
