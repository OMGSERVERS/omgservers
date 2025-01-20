package com.omgservers.service.shard.tenant.impl.operation.tenantFilesArchive.testInterface;

import com.omgservers.schema.model.tenantFilesArchive.TenantFilesArchiveProjectionModel;
import com.omgservers.service.shard.tenant.impl.operation.tenantFilesArchive.SelectActiveTenantFilesArchiveProjectionsByTenantVersionIdOperation;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class SelectActiveTenantFilesArchivesProjectionsByTenantVersionIdOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final SelectActiveTenantFilesArchiveProjectionsByTenantVersionIdOperation
            selectActiveTenantFilesArchiveProjectionsByTenantVersionIdOperation;

    final PgPool pgPool;

    public List<TenantFilesArchiveProjectionModel> execute(final Long tenantId,
                                                           final Long tenantVersionId) {
        return pgPool.withTransaction(
                        sqlConnection -> selectActiveTenantFilesArchiveProjectionsByTenantVersionIdOperation
                                .execute(sqlConnection, 0, tenantId, tenantVersionId))
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
