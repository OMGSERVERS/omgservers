package com.omgservers.application.module.internalModule.impl.operation.deleteServiceAccountOperation;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.UUID;

public interface DeleteServiceAccountOperation {
    Uni<Void> deleteServiceAccount(SqlConnection sqlConnection, Long id);
}
