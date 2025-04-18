package com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolRequest;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.pool.poolRequest.FindPoolRequestRequest;
import com.omgservers.schema.shard.pool.poolRequest.FindPoolRequestResponse;
import io.smallrye.mutiny.Uni;

public interface FindPoolRequestMethod {
    Uni<FindPoolRequestResponse> execute(ShardModel shardModel, FindPoolRequestRequest request);
}
