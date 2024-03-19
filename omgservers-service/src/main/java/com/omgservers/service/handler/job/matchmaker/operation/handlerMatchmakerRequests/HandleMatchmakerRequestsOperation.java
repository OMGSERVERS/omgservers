package com.omgservers.service.handler.job.matchmaker.operation.handlerMatchmakerRequests;

import com.omgservers.model.matchmaker.MatchmakerModel;
import com.omgservers.model.matchmakerChangeOfState.MatchmakerChangeOfStateModel;
import com.omgservers.model.matchmakerState.MatchmakerStateModel;
import io.smallrye.mutiny.Uni;

public interface HandleMatchmakerRequestsOperation {
    Uni<Void> handleMatchmakerRequests(MatchmakerModel matchmaker,
                                       MatchmakerStateModel currentState,
                                       MatchmakerChangeOfStateModel changeOfState);
}
