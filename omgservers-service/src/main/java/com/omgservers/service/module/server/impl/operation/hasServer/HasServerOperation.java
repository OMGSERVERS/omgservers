package com.omgservers.service.module.server.impl.operation.hasServer;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface HasServerOperation {
    Uni<Boolean> hasServer(SqlConnection sqlConnection,
                           int shard,
                           Long id);
}
