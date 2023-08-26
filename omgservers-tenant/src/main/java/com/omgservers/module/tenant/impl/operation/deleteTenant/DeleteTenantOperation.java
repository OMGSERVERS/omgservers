package com.omgservers.module.tenant.impl.operation.deleteTenant;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface DeleteTenantOperation {
    Uni<Boolean> deleteTenant(SqlConnection sqlConnection, int shard, Long id);

    default Boolean deleteTenant(long timeout, PgPool pgPool, int shard, Long id) {
        return pgPool.withTransaction(sqlConnection -> deleteTenant(sqlConnection, shard, id))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
