package com.omgservers.service.module.tenant.operation.testInterface;

import com.omgservers.model.version.VersionModel;
import com.omgservers.service.module.tenant.impl.operation.version.selectVersion.SelectVersionOperation;
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

    final SelectVersionOperation selectVersionOperation;

    final PgPool pgPool;

    public VersionModel selectVersion(final int shard,
                                      final Long tenantId,
                                      final Long id,
                                      final Boolean deleted) {
        return pgPool.withTransaction(sqlConnection -> selectVersionOperation
                        .selectVersion(sqlConnection, shard, tenantId, id))
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
