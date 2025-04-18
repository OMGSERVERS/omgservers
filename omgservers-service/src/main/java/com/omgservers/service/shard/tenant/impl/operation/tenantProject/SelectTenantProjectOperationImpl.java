package com.omgservers.service.shard.tenant.impl.operation.tenantProject;

import com.omgservers.schema.model.project.TenantProjectModel;
import com.omgservers.service.shard.tenant.impl.mapper.TenantProjectModelMapper;
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
class SelectTenantProjectOperationImpl implements SelectTenantProjectOperation {

    final SelectObjectOperation selectObjectOperation;

    final TenantProjectModelMapper tenantProjectModelMapper;

    @Override
    public Uni<TenantProjectModel> execute(final SqlConnection sqlConnection,
                                           final int slot,
                                           final Long tenantId,
                                           final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                slot,
                """
                        select id, idempotency_key, tenant_id, created, modified, config, deleted
                        from $slot.tab_tenant_project
                        where tenant_id = $1 and id = $2
                        limit 1
                        """,
                List.of(
                        tenantId,
                        id
                ),
                "Tenant project",
                tenantProjectModelMapper::execute);
    }
}
