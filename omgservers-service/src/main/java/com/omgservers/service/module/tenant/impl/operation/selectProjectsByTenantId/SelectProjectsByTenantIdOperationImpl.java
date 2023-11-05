package com.omgservers.service.module.tenant.impl.operation.selectProjectsByTenantId;

import com.omgservers.model.project.ProjectModel;
import com.omgservers.service.module.tenant.impl.mapper.ProjectModelMapper;
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
class SelectProjectsByTenantIdOperationImpl implements SelectProjectsByTenantIdOperation {

    final SelectListOperation selectListOperation;

    final ProjectModelMapper projectModelMapper;

    @Override
    public Uni<List<ProjectModel>> selectProjectsByTenantId(final SqlConnection sqlConnection,
                                                            final int shard,
                                                            final Long tenantId,
                                                            final Boolean deleted) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select
                            id, tenant_id, created, modified, deleted
                        from $schema.tab_tenant_project
                        where tenant_id = $1 and deleted = $3
                        """,
                Arrays.asList(
                        tenantId,
                        deleted
                ),
                "Project",
                projectModelMapper::fromRow);
    }
}
