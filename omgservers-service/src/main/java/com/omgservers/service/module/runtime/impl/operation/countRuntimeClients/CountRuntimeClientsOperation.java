package com.omgservers.service.module.runtime.impl.operation.countRuntimeClients;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface CountRuntimeClientsOperation {
    Uni<Integer> countRuntimeClients(SqlConnection sqlConnection,
                                     int shard,
                                     Long runtimeId);
}
