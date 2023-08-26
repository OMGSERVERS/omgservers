package com.omgservers.module.internal.impl.operation.deleteServiceAccount;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteServiceAccountOperation {
    Uni<Void> deleteServiceAccount(SqlConnection sqlConnection, Long id);
}
