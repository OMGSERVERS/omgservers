package com.omgservers.service.shard.client.impl.operation.client;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface VerifyClientExistsOperation {
    Uni<Boolean> hasClient(SqlConnection sqlConnection,
                           int slot,
                           Long id);
}
