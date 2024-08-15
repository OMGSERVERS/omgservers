package com.omgservers.service.module.tenant.impl.operation.project.selectProject;

import com.omgservers.schema.model.project.ProjectModel;
import com.omgservers.service.module.tenant.impl.mapper.ProjectModelMapper;
import com.omgservers.service.operation.selectObject.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectProjectOperationImpl implements SelectProjectOperation {

    final SelectObjectOperation selectObjectOperation;

    final ProjectModelMapper projectModelMapper;

    @Override
    public Uni<ProjectModel> selectProject(final SqlConnection sqlConnection,
                                           final int shard,
                                           final Long tenantId,
                                           final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, tenant_id, created, modified, deleted
                        from $schema.tab_tenant_project
                        where tenant_id = $1 and id = $2
                        limit 1
                        """,
                List.of(
                        tenantId,
                        id
                ),
                "Project",
                projectModelMapper::fromRow);
    }
}
