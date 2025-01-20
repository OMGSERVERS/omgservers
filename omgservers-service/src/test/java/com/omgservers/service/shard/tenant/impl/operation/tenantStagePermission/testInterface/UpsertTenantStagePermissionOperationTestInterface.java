package com.omgservers.service.shard.tenant.impl.operation.tenantStagePermission.testInterface;

import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionModel;
import com.omgservers.service.shard.tenant.impl.operation.tenantStagePermission.UpsertTenantStagePermissionOperation;
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
public class UpsertTenantStagePermissionOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final UpsertTenantStagePermissionOperation upsertTenantStagePermissionOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> execute(final TenantStagePermissionModel permission) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> upsertTenantStagePermissionOperation
                                    .execute(changeContext, sqlConnection, 0, permission))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
