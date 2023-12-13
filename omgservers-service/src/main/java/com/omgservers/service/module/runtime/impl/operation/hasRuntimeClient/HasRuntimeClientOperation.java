package com.omgservers.service.module.runtime.impl.operation.hasRuntimeClient;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface HasRuntimeClientOperation {
    Uni<Boolean> hasRuntimeClient(SqlConnection sqlConnection,
                                  int shard,
                                  Long runtimeId,
                                  Long userId,
                                  Long clientId);
}
