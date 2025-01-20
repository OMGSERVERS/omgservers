package com.omgservers.service.shard.client.impl.operation.client.hasClient;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface HasClientOperation {
    Uni<Boolean> hasClient(SqlConnection sqlConnection,
                           int shard,
                           Long id);
}
