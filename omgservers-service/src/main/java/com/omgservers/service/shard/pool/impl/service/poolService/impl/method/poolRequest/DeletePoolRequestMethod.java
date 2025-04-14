package com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolRequest;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.pool.poolRequest.DeletePoolRequestRequest;
import com.omgservers.schema.shard.pool.poolRequest.DeletePoolRequestResponse;
import io.smallrye.mutiny.Uni;

public interface DeletePoolRequestMethod {
    Uni<DeletePoolRequestResponse> execute(ShardModel shardModel, DeletePoolRequestRequest request);
}
