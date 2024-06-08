package com.omgservers.service.module.root.impl.operation.root.hasRoot;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface HasRootOperation {
    Uni<Boolean> hasRoot(SqlConnection sqlConnection,
                         int shard,
                         Long id);
}
