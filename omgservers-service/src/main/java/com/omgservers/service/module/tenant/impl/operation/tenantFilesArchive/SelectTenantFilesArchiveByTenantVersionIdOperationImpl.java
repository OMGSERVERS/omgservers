package com.omgservers.service.module.tenant.impl.operation.tenantFilesArchive;

import com.omgservers.schema.model.tenantFilesArchive.TenantFilesArchiveModel;
import com.omgservers.service.module.tenant.impl.mapper.TenantFilesArchiveModelMapper;
import com.omgservers.service.operation.server.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectTenantFilesArchiveByTenantVersionIdOperationImpl
        implements SelectTenantFilesArchiveByTenantVersionIdOperation {

    final SelectObjectOperation selectObjectOperation;

    final TenantFilesArchiveModelMapper tenantFilesArchiveModelMapper;

    @Override
    public Uni<TenantFilesArchiveModel> execute(final SqlConnection sqlConnection,
                                                final int shard,
                                                final Long tenantId,
                                                final Long tenantVersionId) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, tenant_id, version_id, created, modified, archive, deleted
                        from $schema.tab_tenant_files_archive
                        where tenant_id = $1 and version_id = $2 and deleted = false
                        order by id desc
                        limit 1
                        """,
                List.of(tenantId, tenantVersionId),
                "Tenant files archive",
                tenantFilesArchiveModelMapper::fromRow);
    }
}
