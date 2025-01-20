package com.omgservers.service.shard.tenant.impl.operation.tenantFilesArchive.testInterface;

import com.omgservers.schema.model.tenantFilesArchive.TenantFilesArchiveModel;
import com.omgservers.service.shard.tenant.impl.operation.tenantFilesArchive.SelectTenantFilesArchiveByTenantVersionIdOperation;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class SelectTenantFilesArchiveByTenantVersionIdOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final SelectTenantFilesArchiveByTenantVersionIdOperation selectTenantFilesArchiveByTenantVersionIdOperation;

    final PgPool pgPool;

    public TenantFilesArchiveModel execute(final Long tenantId,
                                           final Long tenantVersionId) {
        return pgPool.withTransaction(sqlConnection -> selectTenantFilesArchiveByTenantVersionIdOperation
                        .execute(sqlConnection, 0, tenantId, tenantVersionId))
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
