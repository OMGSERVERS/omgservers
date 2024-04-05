package com.omgservers.service.module.pool.impl.operation.poolServerContainer.deletePoolServerContainer;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeletePoolServerContainerOperation {
    Uni<Boolean> deletePoolServerContainer(ChangeContext<?> changeContext,
                                           SqlConnection sqlConnection,
                                           int shard,
                                           Long poolId,
                                           Long serverId,
                                           Long id);
}
