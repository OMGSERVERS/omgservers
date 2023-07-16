package com.omgservers.application.module.tenantModule.impl.operation.insertTenantOperation;

import com.omgservers.application.module.tenantModule.model.tenant.TenantModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface InsertTenantOperation {
    Uni<Void> insertTenant(SqlConnection sqlConnection, int shard, TenantModel tenant);

    default void insertTenant(long timeout, PgPool pgPool, int shard, TenantModel tenant) {
        pgPool.withTransaction(sqlConnection -> insertTenant(sqlConnection, shard, tenant))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
