package com.omgservers.service.module.pool.impl.operation.poolServerContainer.upsertPoolServerContainer;

import com.omgservers.model.poolSeverContainer.PoolServerContainerModel;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertPoolServerContainerOperation {
    Uni<Boolean> upsertPoolServerContainer(ChangeContext<?> changeContext,
                                           SqlConnection sqlConnection,
                                           int shard,
                                           PoolServerContainerModel poolServerContainer);
}
