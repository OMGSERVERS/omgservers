package com.omgservers.application.module.tenantModule.impl.operation.selectTenantOperation;

import com.omgservers.application.module.tenantModule.model.tenant.TenantModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;
import java.util.UUID;

public interface SelectTenantOperation {
    Uni<TenantModel> selectTenant(SqlConnection sqlConnection, int shard, UUID uuid);

    default TenantModel selectTenant(long timeout, PgPool pgPool, int shard, UUID uuid) {
        return pgPool.withTransaction(sqlConnection -> selectTenant(sqlConnection, shard, uuid))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
