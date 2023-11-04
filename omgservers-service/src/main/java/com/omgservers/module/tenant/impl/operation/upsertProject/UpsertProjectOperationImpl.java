package com.omgservers.module.tenant.impl.operation.upsertProject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.model.event.body.ProjectCreatedEventBodyModel;
import com.omgservers.model.project.ProjectModel;
import com.omgservers.factory.LogModelFactory;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.changeObject.ChangeObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.ZoneOffset;
import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertProjectOperationImpl implements UpsertProjectOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertProject(final ChangeContext<?> changeContext,
                                      final SqlConnection sqlConnection,
                                      final int shard,
                                      final ProjectModel project) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_tenant_project(id, tenant_id, created, modified, config)
                        values($1, $2, $3, $4, $5)
                        on conflict (id) do
                        nothing
                        """,
                Arrays.asList(
                        project.getId(),
                        project.getTenantId(),
                        project.getCreated().atOffset(ZoneOffset.UTC),
                        project.getModified().atOffset(ZoneOffset.UTC),
                        getConfigString(project)
                ),
                () -> new ProjectCreatedEventBodyModel(project.getTenantId(), project.getId()),
                () -> logModelFactory.create("Project was created, project=" + project)
        );
    }

    String getConfigString(ProjectModel project) {
        try {
            return objectMapper.writeValueAsString(project.getConfig());
        } catch (IOException e) {
            throw new ServerSideBadRequestException(e.getMessage(), e);
        }
    }
}
