package com.omgservers.service.module.pool.impl.operation.poolServerContainer.selectPoolServerContainerByPoolIdAndRuntimeId;

import com.omgservers.schema.model.poolSeverContainer.PoolServerContainerModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectPoolServerContainerByPoolIdAndRuntimeIdOperation {
    Uni<PoolServerContainerModel> selectPoolServerContainerByPoolIdAndRuntimeId(
            SqlConnection sqlConnection,
            int shard,
            Long poolId,
            Long serverId,
            Long runtimeId);
}
