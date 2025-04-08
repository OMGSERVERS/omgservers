package com.omgservers.service.shard.match.impl.service.matchService.impl.method;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.match.DeleteMatchRequest;
import com.omgservers.schema.module.match.DeleteMatchResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteMatchMethod {
    Uni<DeleteMatchResponse> execute(ShardModel shardModel, DeleteMatchRequest request);
}
