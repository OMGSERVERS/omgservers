package com.omgservers.base.impl.operation.deleteServiceAccountOperation;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteServiceAccountOperation {
    Uni<Void> deleteServiceAccount(SqlConnection sqlConnection, Long id);
}
