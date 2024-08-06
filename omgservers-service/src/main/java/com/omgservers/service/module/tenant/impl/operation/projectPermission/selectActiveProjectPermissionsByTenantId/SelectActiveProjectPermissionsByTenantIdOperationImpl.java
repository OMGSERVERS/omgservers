package com.omgservers.service.module.tenant.impl.operation.projectPermission.selectActiveProjectPermissionsByTenantId;

import com.omgservers.schema.model.projectPermission.ProjectPermissionModel;
import com.omgservers.service.module.tenant.impl.mapper.ProjectPermissionModelMapper;
import com.omgservers.service.server.operation.selectList.SelectListOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectActiveProjectPermissionsByTenantIdOperationImpl
        implements SelectActiveProjectPermissionsByTenantIdOperation {

    final SelectListOperation selectListOperation;

    final ProjectPermissionModelMapper projectPermissionModelMapper;

    @Override
    public Uni<List<ProjectPermissionModel>> selectActiveProjectPermissionsByTenantId(
            final SqlConnection sqlConnection,
            final int shard,
            final Long tenantId) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, tenant_id, project_id, created, modified, user_id, permission, deleted
                        from $schema.tab_tenant_project_permission
                        where tenant_id = $1 and deleted = false
                        order by id asc
                        """,
                List.of(tenantId),
                "Project permission",
                projectPermissionModelMapper::fromRow);
    }
}
