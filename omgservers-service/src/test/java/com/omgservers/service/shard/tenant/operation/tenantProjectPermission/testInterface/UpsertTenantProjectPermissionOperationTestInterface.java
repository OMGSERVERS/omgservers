package com.omgservers.service.shard.tenant.operation.tenantProjectPermission.testInterface;

import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionModel;
import com.omgservers.service.shard.tenant.impl.operation.tenantProjectPermission.UpsertTenantProjectPermissionOperation;
import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class UpsertTenantProjectPermissionOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final UpsertTenantProjectPermissionOperation upsertTenantProjectPermissionOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> execute(final TenantProjectPermissionModel projectPermission) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> upsertTenantProjectPermissionOperation
                                    .execute(changeContext, sqlConnection, 0, projectPermission))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
