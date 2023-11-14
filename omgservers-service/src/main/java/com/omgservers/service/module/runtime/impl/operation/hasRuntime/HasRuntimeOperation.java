package com.omgservers.service.module.runtime.impl.operation.hasRuntime;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface HasRuntimeOperation {
    Uni<Boolean> hasRuntime(SqlConnection sqlConnection,
                            int shard,
                            Long id);
}
