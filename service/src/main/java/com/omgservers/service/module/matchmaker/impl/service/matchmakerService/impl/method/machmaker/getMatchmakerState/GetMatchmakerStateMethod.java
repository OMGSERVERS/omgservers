package com.omgservers.service.module.matchmaker.impl.service.matchmakerService.impl.method.machmaker.getMatchmakerState;

import com.omgservers.model.dto.matchmaker.GetMatchmakerStateRequest;
import com.omgservers.model.dto.matchmaker.GetMatchmakerStateResponse;
import io.smallrye.mutiny.Uni;

public interface GetMatchmakerStateMethod {
    Uni<GetMatchmakerStateResponse> getMatchmakerState(GetMatchmakerStateRequest request);
}
