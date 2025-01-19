package com.omgservers.service.module.pool.impl.operation.poolContainer;

import com.omgservers.schema.model.poolSeverContainer.PoolContainerModel;
import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertPoolContainerOperation {
    Uni<Boolean> execute(ChangeContext<?> changeContext,
                         SqlConnection sqlConnection,
                         int shard,
                         PoolContainerModel poolContainer);
}
