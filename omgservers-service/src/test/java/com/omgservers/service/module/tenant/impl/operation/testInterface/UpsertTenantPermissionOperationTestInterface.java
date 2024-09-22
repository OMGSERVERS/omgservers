package com.omgservers.service.module.tenant.impl.operation.testInterface;

import com.omgservers.schema.model.tenantPermission.TenantPermissionModel;
import com.omgservers.service.module.tenant.impl.operation.tenantPermission.UpsertTenantPermissionOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class UpsertTenantPermissionOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final UpsertTenantPermissionOperation upsertTenantPermissionOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> upsertTenantPermission(final int shard,
                                                         final TenantPermissionModel tenantPermission) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> upsertTenantPermissionOperation
                                    .execute(changeContext, sqlConnection, shard, tenantPermission))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}