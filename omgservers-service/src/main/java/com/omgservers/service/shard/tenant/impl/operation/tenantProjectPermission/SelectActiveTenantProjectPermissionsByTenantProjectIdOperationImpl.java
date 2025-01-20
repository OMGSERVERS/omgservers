package com.omgservers.service.shard.tenant.impl.operation.tenantProjectPermission;

import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionModel;
import com.omgservers.service.shard.tenant.impl.mapper.TenantProjectPermissionModelMapper;
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
class SelectActiveTenantProjectPermissionsByTenantProjectIdOperationImpl
        implements SelectActiveTenantProjectPermissionsByTenantProjectIdOperation {

    final SelectListOperation selectListOperation;

    final TenantProjectPermissionModelMapper tenantProjectPermissionModelMapper;

    @Override
    public Uni<List<TenantProjectPermissionModel>> execute(
            final SqlConnection sqlConnection,
            final int shard,
            final Long tenantId,
            final Long tenantProjectId) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, tenant_id, project_id, created, modified, user_id, permission, deleted
                        from $schema.tab_tenant_project_permission
                        where tenant_id = $1 and project_id = $2 and deleted = false
                        order by id asc
                        """,
                List.of(
                        tenantId,
                        tenantProjectId
                ),
                "Project permission",
                tenantProjectPermissionModelMapper::fromRow);
    }
}
