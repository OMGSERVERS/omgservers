package com.omgservers.service.shard.tenant.impl.operation.tenantDeployment.testInterface;

import com.omgservers.schema.model.tenantDeployment.TenantDeploymentModel;
import com.omgservers.service.shard.tenant.impl.operation.tenantDeployment.SelectActiveTenantDeploymentsByTenantStageIdOperation;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class SelectTenantDeploymentsByTenantStageIdOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final SelectActiveTenantDeploymentsByTenantStageIdOperation
            selectActiveTenantDeploymentsByTenantStageIdOperation;

    final PgPool pgPool;

    public List<TenantDeploymentModel> execute(final Long tenantId,
                                               final Long tenantStageId) {
        return pgPool.withTransaction(sqlConnection -> selectActiveTenantDeploymentsByTenantStageIdOperation
                        .execute(sqlConnection, 0, tenantId, tenantStageId))
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
