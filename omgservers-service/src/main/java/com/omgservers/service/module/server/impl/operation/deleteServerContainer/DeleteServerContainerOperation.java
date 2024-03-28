package com.omgservers.service.module.server.impl.operation.deleteServerContainer;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteServerContainerOperation {
    Uni<Boolean> deleteServerContainer(ChangeContext<?> changeContext,
                                       SqlConnection sqlConnection,
                                       int shard,
                                       Long serverId,
                                       Long id);
}
