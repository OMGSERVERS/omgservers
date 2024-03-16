package com.omgservers.service.module.tenant.impl.operation.upsertProject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.event.body.ProjectCreatedEventBodyModel;
import com.omgservers.model.project.ProjectModel;
import com.omgservers.service.factory.LogModelFactory;
import com.omgservers.service.operation.changeObject.ChangeObjectOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneOffset;
import java.util.List;

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
                        insert into $schema.tab_tenant_project(
                            id, idempotency_key, tenant_id, created, modified, deleted)
                        values($1, $2, $3, $4, $5, $6)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        project.getId(),
                        project.getIdempotencyKey(),
                        project.getTenantId(),
                        project.getCreated().atOffset(ZoneOffset.UTC),
                        project.getModified().atOffset(ZoneOffset.UTC),
                        project.getDeleted()
                ),
                () -> new ProjectCreatedEventBodyModel(project.getTenantId(), project.getId()),
                () -> null
        );
    }
}
