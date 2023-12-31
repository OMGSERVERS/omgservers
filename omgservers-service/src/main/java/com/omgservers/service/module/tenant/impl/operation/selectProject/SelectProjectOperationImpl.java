package com.omgservers.service.module.tenant.impl.operation.selectProject;

import com.omgservers.model.project.ProjectModel;
import com.omgservers.service.module.tenant.impl.mapper.ProjectModelMapper;
import com.omgservers.service.operation.selectObject.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

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
                        select
                            id, tenant_id, created, modified, deleted
                        from $schema.tab_tenant_project
                        where tenant_id = $1 and id = $2
                        limit 1
                        """,
                Arrays.asList(
                        tenantId,
                        id
                ),
                "Project",
                projectModelMapper::fromRow);
    }
}
