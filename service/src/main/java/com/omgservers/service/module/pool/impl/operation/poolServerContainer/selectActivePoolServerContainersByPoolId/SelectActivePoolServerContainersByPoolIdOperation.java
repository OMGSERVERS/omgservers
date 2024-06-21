package com.omgservers.service.module.pool.impl.operation.poolServerContainer.selectActivePoolServerContainersByPoolId;

import com.omgservers.model.poolSeverContainer.PoolServerContainerModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActivePoolServerContainersByPoolIdOperation {
    Uni<List<PoolServerContainerModel>> selectActivePoolServerContainersByPoolId(
            SqlConnection sqlConnection,
            int shard,
            Long poolId);
}
