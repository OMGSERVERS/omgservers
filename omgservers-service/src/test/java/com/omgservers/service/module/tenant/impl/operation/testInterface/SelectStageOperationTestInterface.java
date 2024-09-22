package com.omgservers.service.module.tenant.impl.operation.testInterface;

import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.service.module.tenant.impl.operation.tenantStage.SelectTenantStageOperation;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class SelectStageOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final SelectTenantStageOperation selectTenantStageOperation;

    final PgPool pgPool;

    public TenantStageModel selectStage(final int shard,
                                        final Long tenantId,
                                        final Long id,
                                        final Boolean deleted) {
        return pgPool.withTransaction(sqlConnection -> selectTenantStageOperation
                        .execute(sqlConnection, shard, tenantId, id))
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
