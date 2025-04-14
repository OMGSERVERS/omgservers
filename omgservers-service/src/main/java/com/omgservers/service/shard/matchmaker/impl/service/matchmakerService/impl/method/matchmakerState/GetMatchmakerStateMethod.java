package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerState;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.matchmaker.matchmakerState.GetMatchmakerStateRequest;
import com.omgservers.schema.shard.matchmaker.matchmakerState.GetMatchmakerStateResponse;
import io.smallrye.mutiny.Uni;

public interface GetMatchmakerStateMethod {
    Uni<GetMatchmakerStateResponse> execute(ShardModel shardModel, GetMatchmakerStateRequest request);
}
