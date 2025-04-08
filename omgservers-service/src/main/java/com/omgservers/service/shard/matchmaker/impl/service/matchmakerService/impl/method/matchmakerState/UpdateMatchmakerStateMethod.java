package com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.impl.method.matchmakerState;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.matchmaker.matchmakerState.UpdateMatchmakerStateRequest;
import com.omgservers.schema.module.matchmaker.matchmakerState.UpdateMatchmakerStateResponse;
import io.smallrye.mutiny.Uni;

public interface UpdateMatchmakerStateMethod {
    Uni<UpdateMatchmakerStateResponse> execute(ShardModel shardModel, UpdateMatchmakerStateRequest request);
}
