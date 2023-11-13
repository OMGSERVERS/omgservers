package com.omgservers.service.module.tenant.impl.operation.selectActiveProjectPermissionsByProjectId;

import com.omgservers.model.projectPermission.ProjectPermissionModel;
import com.omgservers.service.module.tenant.impl.mapper.ProjectPermissionModelMapper;
import com.omgservers.service.operation.selectList.SelectListOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectActiveProjectPermissionsByProjectIdOperationImpl
        implements SelectActiveProjectPermissionsByProjectIdOperation {

    final SelectListOperation selectListOperation;

    final ProjectPermissionModelMapper projectPermissionModelMapper;

    @Override
    public Uni<List<ProjectPermissionModel>> selectActiveProjectPermissionsByProjectId(
            final SqlConnection sqlConnection,
            final int shard,
            final Long tenantId,
            final Long projectId) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select
                            id, tenant_id, project_id, created, modified, user_id, permission, deleted
                        from $schema.tab_tenant_project_permission
                        where tenant_id = $1 and project_id = $2 and deleted = false
                        """,
                Arrays.asList(
                        tenantId,
                        projectId
                ),
                "Project permission",
                projectPermissionModelMapper::fromRow);
    }
}
