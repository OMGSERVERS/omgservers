package com.omgservers.module.tenant.impl.operation.upsertTenantPermission;

import com.omgservers.model.tenantPermission.TenantPermissionModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface UpsertTenantPermissionOperation {
    Uni<Boolean> upsertTenantPermission(SqlConnection sqlConnection, int shard, TenantPermissionModel permission);

    default Boolean upsertTenantPermission(long timeout, PgPool pgPool, int shard, TenantPermissionModel permission) {
        return pgPool.withTransaction(sqlConnection -> upsertTenantPermission(sqlConnection, shard, permission))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
