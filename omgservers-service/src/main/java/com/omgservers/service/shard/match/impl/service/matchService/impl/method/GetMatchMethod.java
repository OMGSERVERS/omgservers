package com.omgservers.service.shard.match.impl.service.matchService.impl.method;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.match.GetMatchRequest;
import com.omgservers.schema.module.match.GetMatchResponse;
import io.smallrye.mutiny.Uni;

public interface GetMatchMethod {
    Uni<GetMatchResponse> execute(ShardModel shardModel, GetMatchRequest request);
}
