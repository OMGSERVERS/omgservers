package com.omgservers.service.module.tenant.operation.testInterface;

import com.omgservers.model.projectPermission.ProjectPermissionEnum;
import com.omgservers.service.module.tenant.impl.operation.hasProjectPermission.HasProjectPermissionOperation;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class HasProjectPermissionOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final HasProjectPermissionOperation hasProjectPermissionOperation;

    final PgPool pgPool;

    public Boolean hasProjectPermission(final int shard,
                                       Long tenantId,
                                       Long projectId,
                                       Long userId,
                                       ProjectPermissionEnum permission) {
        return pgPool.withTransaction(sqlConnection -> hasProjectPermissionOperation
                        .hasProjectPermission(sqlConnection, shard, tenantId, projectId, userId, permission))
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
