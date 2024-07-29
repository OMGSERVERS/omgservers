package com.omgservers.service.module.pool.impl.operation.poolServer.deletePoolServer;

import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeletePoolServerOperation {
    Uni<Boolean> deletePoolServer(ChangeContext<?> changeContext,
                                  SqlConnection sqlConnection,
                                  int shard,
                                  Long poolId,
                                  Long id);
}
