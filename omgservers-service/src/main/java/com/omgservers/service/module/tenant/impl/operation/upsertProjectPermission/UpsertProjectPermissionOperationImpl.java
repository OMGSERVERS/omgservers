package com.omgservers.service.module.tenant.impl.operation.upsertProjectPermission;

import com.omgservers.model.projectPermission.ProjectPermissionModel;
import com.omgservers.service.factory.LogModelFactory;
import com.omgservers.service.operation.changeObject.ChangeObjectOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneOffset;
import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertProjectPermissionOperationImpl implements UpsertProjectPermissionOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> upsertProjectPermission(final ChangeContext<?> changeContext,
                                                final SqlConnection sqlConnection,
                                                final int shard,
                                                final ProjectPermissionModel projectPermission) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_tenant_project_permission(
                            id, idempotency_key, tenant_id, project_id, created, modified, user_id, permission, deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8, $9)
                        on conflict (id) do
                        nothing
                        """,
                Arrays.asList(
                        projectPermission.getId(),
                        projectPermission.getIdempotencyKey(),
                        projectPermission.getTenantId(),
                        projectPermission.getProjectId(),
                        projectPermission.getCreated().atOffset(ZoneOffset.UTC),
                        projectPermission.getModified().atOffset(ZoneOffset.UTC),
                        projectPermission.getUserId(),
                        projectPermission.getPermission(),
                        projectPermission.getDeleted()
                ),
                () -> null,
                () -> logModelFactory.create("Project permission was inserted, projectPermission=" + projectPermission)
        );
    }
}
