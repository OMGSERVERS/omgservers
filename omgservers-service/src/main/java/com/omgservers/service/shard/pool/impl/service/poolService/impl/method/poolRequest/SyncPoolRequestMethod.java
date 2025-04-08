package com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolRequest;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.pool.poolRequest.SyncPoolRequestRequest;
import com.omgservers.schema.module.pool.poolRequest.SyncPoolRequestResponse;
import io.smallrye.mutiny.Uni;

public interface SyncPoolRequestMethod {
    Uni<SyncPoolRequestResponse> execute(ShardModel shardModel, SyncPoolRequestRequest request);
}
