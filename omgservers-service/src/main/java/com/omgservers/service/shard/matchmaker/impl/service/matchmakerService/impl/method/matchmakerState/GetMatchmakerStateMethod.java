package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerState;

import com.omgservers.schema.module.matchmaker.matchmakerState.GetMatchmakerStateRequest;
import com.omgservers.schema.module.matchmaker.matchmakerState.GetMatchmakerStateResponse;
import io.smallrye.mutiny.Uni;

public interface GetMatchmakerStateMethod {
    Uni<GetMatchmakerStateResponse> execute(GetMatchmakerStateRequest request);
}
