package com.omgservers.service.module.tenant.impl.operation.upsertTenantPermission;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.model.tenantPermission.TenantPermissionModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface UpsertTenantPermissionOperation {
    Uni<Boolean> upsertTenantPermission(final ChangeContext<?> changeContext,
                                        SqlConnection sqlConnection,
                                        int shard,
                                        TenantPermissionModel permission);

    default Boolean upsertTenantPermission(long timeout,
                                           PgPool pgPool,
                                           int shard,
                                           TenantPermissionModel permission) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection ->
                            upsertTenantPermission(changeContext, sqlConnection, shard, permission));
                })
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
