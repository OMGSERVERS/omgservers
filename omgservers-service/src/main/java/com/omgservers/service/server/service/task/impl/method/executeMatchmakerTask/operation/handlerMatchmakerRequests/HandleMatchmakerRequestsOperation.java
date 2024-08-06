package com.omgservers.service.server.service.task.impl.method.executeMatchmakerTask.operation.handlerMatchmakerRequests;

import com.omgservers.schema.model.matchmaker.MatchmakerModel;
import com.omgservers.schema.model.matchmakerChangeOfState.MatchmakerChangeOfStateModel;
import com.omgservers.schema.model.matchmakerState.MatchmakerStateModel;
import io.smallrye.mutiny.Uni;

public interface HandleMatchmakerRequestsOperation {
    Uni<Void> handleMatchmakerRequests(MatchmakerModel matchmaker,
                                       MatchmakerStateModel currentState,
                                       MatchmakerChangeOfStateModel changeOfState);
}
