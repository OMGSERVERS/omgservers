package com.omgservers.service.shard.tenant.impl.operation.tenantVersion;

import com.omgservers.schema.model.tenantVersion.TenantVersionProjectionModel;
import com.omgservers.service.shard.tenant.impl.mapper.TenantVersionProjectionModelMapper;
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
class SelectActiveTenantVersionProjectionsByTenantProjectIdOperationImpl
        implements SelectActiveTenantVersionProjectionsByTenantProjectIdOperation {

    final SelectListOperation selectListOperation;

    final TenantVersionProjectionModelMapper tenantVersionProjectionModelMapper;

    @Override
    public Uni<List<TenantVersionProjectionModel>> execute(final SqlConnection sqlConnection,
                                                           final int slot,
                                                           final Long tenantId,
                                                           final Long tenantProjectId) {
        return selectListOperation.selectList(
                sqlConnection,
                slot,
                """
                        select id, idempotency_key, tenant_id, project_id, created, modified, deleted
                        from $slot.tab_tenant_version
                        where tenant_id = $1 and project_id = $2 and deleted = false
                        order by id asc
                        """,
                List.of(
                        tenantId,
                        tenantProjectId
                ),
                "Tenant version projection",
                tenantVersionProjectionModelMapper::execute);
    }
}
