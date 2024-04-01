package com.omgservers.service.module.pool.impl.operation.poolRuntimeServerContainerRequest.selectActivePoolRuntimeServerContainerRequestsByPoolId;

import com.omgservers.model.poolRuntimeServerContainerRequest.PoolRuntimeServerContainerRequestModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActivePoolRuntimeServerContainerRequestsByPoolIdOperation {
    Uni<List<PoolRuntimeServerContainerRequestModel>> selectActivePoolRuntimeServerContainerRequestsByPoolId(
            SqlConnection sqlConnection,
            int shard,
            Long poolId);
}
