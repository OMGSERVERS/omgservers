package com.omgservers.service.shard.tenant.impl.operation.tenantFilesArchive.testInterface;

import com.omgservers.schema.model.tenantFilesArchive.TenantFilesArchiveProjectionModel;
import com.omgservers.service.shard.tenant.impl.operation.tenantFilesArchive.SelectActiveTenantFilesArchiveProjectionsByTenantIdOperation;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class SelectActiveTenantFilesArchivesProjectionsByTenantIdOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final SelectActiveTenantFilesArchiveProjectionsByTenantIdOperation
            selectActiveTenantFilesArchiveProjectionsByTenantIdOperation;

    final PgPool pgPool;

    public List<TenantFilesArchiveProjectionModel> execute(final Long tenantId) {
        return pgPool.withTransaction(sqlConnection -> selectActiveTenantFilesArchiveProjectionsByTenantIdOperation
                        .execute(sqlConnection, 0, tenantId))
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
