package com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolRequest;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.pool.poolRequest.ViewPoolRequestsRequest;
import com.omgservers.schema.module.pool.poolRequest.ViewPoolRequestsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewPoolRequestsMethod {
    Uni<ViewPoolRequestsResponse> execute(ShardModel shardModel, ViewPoolRequestsRequest request);
}
