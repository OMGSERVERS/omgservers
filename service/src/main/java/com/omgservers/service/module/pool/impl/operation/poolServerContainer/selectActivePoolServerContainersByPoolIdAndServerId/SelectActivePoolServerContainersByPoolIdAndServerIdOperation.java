package com.omgservers.service.module.pool.impl.operation.poolServerContainer.selectActivePoolServerContainersByPoolIdAndServerId;

import com.omgservers.schema.model.poolSeverContainer.PoolServerContainerModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActivePoolServerContainersByPoolIdAndServerIdOperation {
    Uni<List<PoolServerContainerModel>> selectActivePoolServerContainersByPoolIdAndServerId(
            SqlConnection sqlConnection,
            int shard,
            Long poolId,
            Long serverId);
}
