package com.omgservers.service.module.pool.impl.operation.poolRuntimeServerContainerRef.selectActivePoolRuntimeServerContainerRefsByPoolId;

import com.omgservers.model.poolRuntimeSeverContainerRef.PoolRuntimeServerContainerRefModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActivePoolRuntimeServerContainerRefsByPoolIdOperation {
    Uni<List<PoolRuntimeServerContainerRefModel>> selectActivePoolRuntimeServerContainerRefsByPoolId(
            SqlConnection sqlConnection,
            int shard,
            Long poolId);
}
