package com.omgservers.base.impl.operation.deleteIndexOperation;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteIndexOperation {
    Uni<Void> deleteIndex(SqlConnection sqlConnection, Long id);
}
