package com.omgservers.module.internal.impl.operation.deleteServiceAccount;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface DeleteServiceAccountOperation {
    Uni<Boolean> deleteServiceAccount(SqlConnection sqlConnection, Long id);

    default Boolean deleteServiceAccount(long timeout, PgPool pgPool, Long id) {
        return pgPool.withTransaction(sqlConnection -> deleteServiceAccount(sqlConnection, id))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
