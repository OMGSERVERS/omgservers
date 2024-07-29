package com.omgservers.service.module.pool.impl.operation.poolServerContainer.upsertPoolServerContainer;

import com.omgservers.schema.model.poolSeverContainer.PoolServerContainerModel;
import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertPoolServerContainerOperation {
    Uni<Boolean> upsertPoolServerContainer(ChangeContext<?> changeContext,
                                           SqlConnection sqlConnection,
                                           int shard,
                                           PoolServerContainerModel poolServerContainer);
}
