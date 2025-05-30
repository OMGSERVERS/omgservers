package com.omgservers.service.shard.tenant.impl.operation.tenantProject;

import com.omgservers.schema.model.project.TenantProjectModel;
import com.omgservers.service.shard.tenant.impl.mapper.TenantProjectModelMapper;
import com.omgservers.service.operation.server.SelectListOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectActiveTenantProjectsByTenantIdOperationImpl implements SelectActiveTenantProjectsByTenantIdOperation {

    final SelectListOperation selectListOperation;

    final TenantProjectModelMapper tenantProjectModelMapper;

    @Override
    public Uni<List<TenantProjectModel>> execute(final SqlConnection sqlConnection,
                                                 final int slot,
                                                 final Long tenantId) {
        return selectListOperation.selectList(
                sqlConnection,
                slot,
                """
                        select id, idempotency_key, tenant_id, created, modified, config, deleted
                        from $slot.tab_tenant_project
                        where tenant_id = $1 and deleted = false
                        order by id asc
                        """,
                Collections.singletonList(tenantId),
                "Tenant project",
                tenantProjectModelMapper::execute);
    }
}
