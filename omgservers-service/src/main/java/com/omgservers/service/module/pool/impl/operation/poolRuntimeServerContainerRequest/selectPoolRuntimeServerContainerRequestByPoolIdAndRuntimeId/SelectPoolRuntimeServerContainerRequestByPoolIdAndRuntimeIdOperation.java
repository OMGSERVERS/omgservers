package com.omgservers.service.module.pool.impl.operation.poolRuntimeServerContainerRequest.selectPoolRuntimeServerContainerRequestByPoolIdAndRuntimeId;

import com.omgservers.model.poolRuntimeServerContainerRequest.PoolRuntimeServerContainerRequestModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectPoolRuntimeServerContainerRequestByPoolIdAndRuntimeIdOperation {
    Uni<PoolRuntimeServerContainerRequestModel> selectPoolRuntimeServerContainerRequestByPoolIdAndRuntimeId(
            SqlConnection sqlConnection,
            int shard,
            Long poolId,
            Long runtimeId);
}
