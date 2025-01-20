package com.omgservers.service.shard.tenant.impl.operation.tenantProject.testInterface;

import com.omgservers.schema.model.project.TenantProjectModel;
import com.omgservers.service.shard.tenant.impl.operation.tenantProject.SelectTenantProjectOperation;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class SelectTenantProjectOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final SelectTenantProjectOperation selectTenantProjectOperation;

    final PgPool pgPool;

    public TenantProjectModel execute(final Long tenantId,
                                      final Long id) {
        return pgPool.withTransaction(sqlConnection -> selectTenantProjectOperation
                        .execute(sqlConnection, 0, tenantId, id))
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
