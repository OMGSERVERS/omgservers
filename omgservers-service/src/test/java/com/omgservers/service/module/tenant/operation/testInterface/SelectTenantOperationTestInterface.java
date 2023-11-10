package com.omgservers.service.module.tenant.operation.testInterface;

import com.omgservers.model.tenant.TenantModel;
import com.omgservers.service.module.tenant.impl.operation.selectTenant.SelectTenantOperation;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class SelectTenantOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final SelectTenantOperation selectTenantOperation;

    final PgPool pgPool;

    public TenantModel selectTenant(final int shard,
                                    final Long id,
                                    final Boolean deleted) {
        return pgPool.withTransaction(sqlConnection -> selectTenantOperation
                        .selectTenant(sqlConnection, shard, id))
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
