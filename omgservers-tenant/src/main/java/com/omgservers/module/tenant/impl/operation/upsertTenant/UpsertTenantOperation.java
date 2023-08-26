package com.omgservers.module.tenant.impl.operation.upsertTenant;

import com.omgservers.model.tenant.TenantModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface UpsertTenantOperation {
    Uni<Boolean> upsertTenant(SqlConnection sqlConnection, int shard, TenantModel tenant);

    default Boolean upsertTenant(long timeout, PgPool pgPool, int shard, TenantModel tenant) {
        return pgPool.withTransaction(sqlConnection -> upsertTenant(sqlConnection, shard, tenant))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
