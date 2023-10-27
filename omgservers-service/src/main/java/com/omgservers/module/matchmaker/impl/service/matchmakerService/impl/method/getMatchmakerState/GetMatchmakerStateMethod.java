package com.omgservers.module.matchmaker.impl.service.matchmakerService.impl.method.getMatchmakerState;

import com.omgservers.dto.matchmaker.GetMatchmakerStateRequest;
import com.omgservers.dto.matchmaker.GetMatchmakerStateResponse;
import io.smallrye.mutiny.Uni;

public interface GetMatchmakerStateMethod {
    Uni<GetMatchmakerStateResponse> getMatchmakerState(GetMatchmakerStateRequest request);
}
