package com.omgservers.service.shard.tenant.impl.operation.tenantDeployment.testInterface;

import com.omgservers.schema.model.tenantDeployment.TenantDeploymentModel;
import com.omgservers.service.shard.tenant.impl.operation.tenantDeployment.SelectActiveTenantDeploymentsByTenantIdOperation;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class SelectTenantDeploymentsByTenantIdOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final SelectActiveTenantDeploymentsByTenantIdOperation
            selectActiveTenantDeploymentsByTenantIdOperation;

    final PgPool pgPool;

    public List<TenantDeploymentModel> execute(final Long tenantId) {
        return pgPool.withTransaction(sqlConnection -> selectActiveTenantDeploymentsByTenantIdOperation
                        .execute(sqlConnection, 0, tenantId))
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
