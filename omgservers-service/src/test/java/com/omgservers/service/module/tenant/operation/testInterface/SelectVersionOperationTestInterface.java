package com.omgservers.service.module.tenant.operation.testInterface;

import com.omgservers.schema.model.tenantVersion.TenantVersionModel;
import com.omgservers.service.module.tenant.impl.operation.tenantVersion.SelectTenantVersionOperation;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class SelectVersionOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final SelectTenantVersionOperation selectTenantVersionOperation;

    final PgPool pgPool;

    public TenantVersionModel selectVersion(final int shard,
                                            final Long tenantId,
                                            final Long id,
                                            final Boolean deleted) {
        return pgPool.withTransaction(sqlConnection -> selectTenantVersionOperation
                        .execute(sqlConnection, shard, tenantId, id))
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
