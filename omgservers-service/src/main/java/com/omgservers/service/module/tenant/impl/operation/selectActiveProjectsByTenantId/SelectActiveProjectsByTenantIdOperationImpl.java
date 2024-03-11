package com.omgservers.service.module.tenant.impl.operation.selectActiveProjectsByTenantId;

import com.omgservers.model.project.ProjectModel;
import com.omgservers.service.module.tenant.impl.mapper.ProjectModelMapper;
import com.omgservers.service.operation.selectList.SelectListOperation;
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
class SelectActiveProjectsByTenantIdOperationImpl implements SelectActiveProjectsByTenantIdOperation {

    final SelectListOperation selectListOperation;

    final ProjectModelMapper projectModelMapper;

    @Override
    public Uni<List<ProjectModel>> selectActiveProjectsByTenantId(final SqlConnection sqlConnection,
                                                                  final int shard,
                                                                  final Long tenantId) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, tenant_id, created, modified, deleted
                        from $schema.tab_tenant_project
                        where tenant_id = $1 and deleted = false
                        order by id asc
                        """,
                Collections.singletonList(tenantId),
                "Project",
                projectModelMapper::fromRow);
    }
}
