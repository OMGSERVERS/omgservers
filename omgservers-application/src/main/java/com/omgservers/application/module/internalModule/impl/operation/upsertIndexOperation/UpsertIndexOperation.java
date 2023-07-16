package com.omgservers.application.module.internalModule.impl.operation.upsertIndexOperation;

import com.omgservers.application.module.internalModule.model.index.IndexModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertIndexOperation {
    Uni<Void> upsertIndex(SqlConnection sqlConnection, IndexModel index);
}
