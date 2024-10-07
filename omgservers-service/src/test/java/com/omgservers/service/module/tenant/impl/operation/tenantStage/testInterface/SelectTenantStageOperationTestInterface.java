package com.omgservers.service.module.tenant.impl.operation.tenantStage.testInterface;

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
public class SelectTenantStageOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final SelectTenantStageOperation selectTenantStageOperation;

    final PgPool pgPool;

    public TenantStageModel execute(final Long tenantId,
                                    final Long id) {
        return pgPool.withTransaction(sqlConnection -> selectTenantStageOperation
                        .execute(sqlConnection, 0, tenantId, id))
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
