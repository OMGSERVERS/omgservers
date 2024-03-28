package com.omgservers.service.module.server.impl.operation.deleteServer;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteServerOperation {
    Uni<Boolean> deleteServer(ChangeContext<?> changeContext,
                              SqlConnection sqlConnection,
                              int shard,
                              Long id);
}
