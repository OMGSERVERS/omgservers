package com.omgservers.service.shard.tenant.operation.tenantStageCommand.testInterface;

import com.omgservers.schema.model.tenantStageCommand.TenantStageCommandModel;
import com.omgservers.service.shard.tenant.impl.operation.tenantStageCommand.SelectActiveTenantStageCommandsByTenantStageIdOperation;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class SelectActiveTenantStageCommandsByTenantStageIdOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final SelectActiveTenantStageCommandsByTenantStageIdOperation selectActiveTenantStageCommandsByTenantStageIdOperation;

    final PgPool pgPool;

    public List<TenantStageCommandModel> execute(final Long tenantId,
                                                final Long tenantStageId) {
        return pgPool.withTransaction(sqlConnection -> selectActiveTenantStageCommandsByTenantStageIdOperation
                        .execute(sqlConnection, 0, tenantId, tenantStageId))
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}