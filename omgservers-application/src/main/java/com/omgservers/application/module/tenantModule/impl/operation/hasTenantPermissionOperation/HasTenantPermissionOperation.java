package com.omgservers.application.module.tenantModule.impl.operation.hasTenantPermissionOperation;

import com.omgservers.application.module.tenantModule.model.tenant.TenantPermissionEnum;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;
import java.util.UUID;

public interface HasTenantPermissionOperation {
    Uni<Boolean> hasTenantPermission(SqlConnection sqlConnection,
                                     int shard,
                                     UUID tenant,
                                     UUID user,
                                     TenantPermissionEnum permission);

    default Boolean hasTenantPermission(long timeout,
                                        PgPool pgPool,
                                        int shard,
                                        UUID tenant,
                                        UUID user,
                                        TenantPermissionEnum permission) {
        return pgPool.withTransaction(sqlConnection -> hasTenantPermission(sqlConnection, shard, tenant, user, permission))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
