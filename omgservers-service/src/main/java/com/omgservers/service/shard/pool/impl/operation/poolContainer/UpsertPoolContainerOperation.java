package com.omgservers.service.shard.pool.impl.operation.poolContainer;

import com.omgservers.schema.model.poolContainer.PoolContainerModel;
import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertPoolContainerOperation {
    Uni<Boolean> execute(ChangeContext<?> changeContext,
                         SqlConnection sqlConnection,
                         int slot,
                         PoolContainerModel poolContainer);
}
