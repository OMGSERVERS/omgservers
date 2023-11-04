package com.omgservers.service.module.tenant.impl.operation.selectProject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.model.project.ProjectConfigModel;
import com.omgservers.model.project.ProjectModel;
import com.omgservers.service.operation.selectObject.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectProjectOperationImpl implements SelectProjectOperation {

    final SelectObjectOperation selectObjectOperation;

    final ObjectMapper objectMapper;

    @Override
    public Uni<ProjectModel> selectProject(final SqlConnection sqlConnection,
                                           final int shard,
                                           final Long tenantId,
                                           final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select id, tenant_id, created, modified, config
                        from $schema.tab_tenant_project
                        where tenant_id = $1 and id = $2
                        limit 1
                        """,
                Arrays.asList(tenantId, id),
                "Project",
                this::createProject);
    }

    ProjectModel createProject(Row row) {
        ProjectModel project = new ProjectModel();
        project.setId(row.getLong("id"));
        project.setTenantId(row.getLong("tenant_id"));
        project.setCreated(row.getOffsetDateTime("created").toInstant());
        project.setModified(row.getOffsetDateTime("modified").toInstant());

        try {
            project.setConfig(objectMapper.readValue(row.getString("config"), ProjectConfigModel.class));
        } catch (IOException e) {
            throw new ServerSideConflictException("project config can't be parsed, project=" + project, e);
        }

        return project;
    }
}
