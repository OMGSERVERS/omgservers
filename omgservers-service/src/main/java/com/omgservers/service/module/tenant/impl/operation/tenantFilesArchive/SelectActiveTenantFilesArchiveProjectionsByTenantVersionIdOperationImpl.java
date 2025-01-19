package com.omgservers.service.module.tenant.impl.operation.tenantFilesArchive;

import com.omgservers.schema.model.tenantFilesArchive.TenantFilesArchiveProjectionModel;
import com.omgservers.service.module.tenant.impl.mapper.TenantFilesArchiveProjectionModelMapper;
import com.omgservers.service.operation.server.SelectListOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectActiveTenantFilesArchiveProjectionsByTenantVersionIdOperationImpl
        implements SelectActiveTenantFilesArchiveProjectionsByTenantVersionIdOperation {

    final SelectListOperation selectListOperation;

    final TenantFilesArchiveProjectionModelMapper tenantFilesArchiveProjectionModelMapper;

    @Override
    public Uni<List<TenantFilesArchiveProjectionModel>> execute(
            final SqlConnection sqlConnection,
            final int shard,
            final Long tenantId,
            final Long tenantVersionId) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select
                            id, idempotency_key, tenant_id, version_id, created, modified, deleted
                        from $schema.tab_tenant_files_archive
                        where tenant_id = $1 and version_id = $2 and deleted = false
                        order by id asc
                        """,
                List.of(tenantId, tenantVersionId),
                "Tenant files archive projections",
                tenantFilesArchiveProjectionModelMapper::fromRow);
    }
}
