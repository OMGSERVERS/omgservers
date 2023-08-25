package com.omgservers.base.impl.operation.upsertIndexOperation;

import com.omgservers.model.index.IndexModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertIndexOperation {
    Uni<Void> upsertIndex(SqlConnection sqlConnection, IndexModel index);
}
