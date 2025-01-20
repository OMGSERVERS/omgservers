package com.omgservers.service.shard.tenant.impl.operation.tenantDeployment.testInterface;

import com.omgservers.schema.model.tenantDeployment.TenantDeploymentModel;
import com.omgservers.service.shard.tenant.impl.operation.tenantDeployment.SelectTenantDeploymentOperation;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class SelectTenantDeploymentOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final SelectTenantDeploymentOperation selectTenantDeploymentOperation;

    final PgPool pgPool;

    public TenantDeploymentModel execute(final Long tenantId,
                                         final Long id) {
        return pgPool.withTransaction(sqlConnection -> selectTenantDeploymentOperation
                        .execute(sqlConnection, 0, tenantId, id))
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
