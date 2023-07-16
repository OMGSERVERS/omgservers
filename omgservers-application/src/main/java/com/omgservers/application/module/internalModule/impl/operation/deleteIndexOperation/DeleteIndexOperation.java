package com.omgservers.application.module.internalModule.impl.operation.deleteIndexOperation;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.UUID;

public interface DeleteIndexOperation {
    Uni<Void> deleteIndex(SqlConnection sqlConnection, UUID uuid);
}
