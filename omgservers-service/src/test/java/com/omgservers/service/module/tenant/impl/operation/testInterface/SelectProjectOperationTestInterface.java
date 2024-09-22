package com.omgservers.service.module.tenant.impl.operation.testInterface;

import com.omgservers.schema.model.project.TenantProjectModel;
import com.omgservers.service.module.tenant.impl.operation.tenantProject.SelectTenantProjectOperation;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class SelectProjectOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final SelectTenantProjectOperation selectTenantProjectOperation;

    final PgPool pgPool;

    public TenantProjectModel selectProject(final int shard,
                                            final Long tenantId,
                                            final Long id,
                                            final Boolean deleted) {
        return pgPool.withTransaction(sqlConnection -> selectTenantProjectOperation
                        .execute(sqlConnection, shard, tenantId, id))
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
