package com.omgservers.service.shard.tenant.impl.operation.tenantDeployment.testInterface;

import com.omgservers.schema.model.tenantDeployment.TenantDeploymentModel;
import com.omgservers.service.shard.tenant.impl.operation.tenantDeployment.SelectActiveTenantDeploymentsByTenantVersionIdOperation;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class SelectTenantDeploymentsByTenantVersionIdOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final SelectActiveTenantDeploymentsByTenantVersionIdOperation
            selectActiveTenantDeploymentsByTenantVersionIdOperation;

    final PgPool pgPool;

    public List<TenantDeploymentModel> execute(final Long tenantId,
                                               final Long tenantVersionId) {
        return pgPool.withTransaction(sqlConnection -> selectActiveTenantDeploymentsByTenantVersionIdOperation
                        .execute(sqlConnection, 0, tenantId, tenantVersionId))
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
