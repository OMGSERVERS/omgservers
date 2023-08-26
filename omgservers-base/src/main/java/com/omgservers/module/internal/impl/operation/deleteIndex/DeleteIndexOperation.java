package com.omgservers.module.internal.impl.operation.deleteIndex;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteIndexOperation {
    Uni<Void> deleteIndex(SqlConnection sqlConnection, Long id);
}
