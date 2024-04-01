package com.omgservers.service.module.pool.impl.operation.poolRuntimeServerContainerRef.selectPoolRuntimeServerContainerRefByPoolIdAndContainerId;

import com.omgservers.model.poolRuntimeSeverContainerRef.PoolRuntimeServerContainerRefModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectPoolRuntimeServerContainerRefByPoolIdAndContainerIdOperation {
    Uni<PoolRuntimeServerContainerRefModel> selectPoolRuntimeServerContainerRefByPoolIdAndContainerId(
            SqlConnection sqlConnection,
            int shard,
            Long poolId,
            Long serverId,
            Long containerId);
}
