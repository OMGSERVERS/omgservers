package com.omgservers.service.shard.tenant.operation.tenant.testInterface;

import com.omgservers.service.shard.tenant.impl.operation.tenant.VerifyTenantExistsOperation;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class VerifyTenantExistsOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final VerifyTenantExistsOperation verifyTenantExistsOperation;

    final PgPool pgPool;

    public Boolean execute(final Long id) {
        return pgPool.withTransaction(sqlConnection -> verifyTenantExistsOperation
                        .execute(sqlConnection, 0, id))
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
