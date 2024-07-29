package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.machmaker.getMatchmakerState;

import com.omgservers.schema.module.matchmaker.GetMatchmakerStateRequest;
import com.omgservers.schema.module.matchmaker.GetMatchmakerStateResponse;
import io.smallrye.mutiny.Uni;

public interface GetMatchmakerStateMethod {
    Uni<GetMatchmakerStateResponse> getMatchmakerState(GetMatchmakerStateRequest request);
}
