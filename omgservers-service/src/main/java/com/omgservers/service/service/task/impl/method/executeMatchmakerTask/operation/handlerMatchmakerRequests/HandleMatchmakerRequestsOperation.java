package com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation.handlerMatchmakerRequests;

import com.omgservers.schema.model.matchmaker.MatchmakerModel;
import com.omgservers.schema.model.matchmakerChangeOfState.MatchmakerChangeOfStateDto;
import com.omgservers.schema.model.matchmakerState.MatchmakerStateDto;
import io.smallrye.mutiny.Uni;

public interface HandleMatchmakerRequestsOperation {
    Uni<Void> handleMatchmakerRequests(MatchmakerModel matchmaker,
                                       MatchmakerStateDto currentState,
                                       MatchmakerChangeOfStateDto changeOfState);
}
