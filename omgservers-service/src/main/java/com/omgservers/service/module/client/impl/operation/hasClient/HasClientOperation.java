package com.omgservers.service.module.client.impl.operation.hasClient;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface HasClientOperation {
    Uni<Boolean> hasClient(SqlConnection sqlConnection,
                           int shard,
                           Long id);
}
