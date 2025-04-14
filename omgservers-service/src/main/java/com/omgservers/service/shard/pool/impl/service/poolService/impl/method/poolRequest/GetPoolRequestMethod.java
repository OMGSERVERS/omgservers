package com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolRequest;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.pool.poolRequest.GetPoolRequestRequest;
import com.omgservers.schema.shard.pool.poolRequest.GetPoolRequestResponse;
import io.smallrye.mutiny.Uni;

public interface GetPoolRequestMethod {
    Uni<GetPoolRequestResponse> execute(ShardModel shardModel, GetPoolRequestRequest request);
}
