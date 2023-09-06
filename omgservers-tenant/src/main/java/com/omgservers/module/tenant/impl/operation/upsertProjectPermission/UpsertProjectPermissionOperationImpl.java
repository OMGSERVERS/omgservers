package com.omgservers.module.tenant.impl.operation.upsertProjectPermission;

import com.omgservers.model.projectPermission.ProjectPermissionModel;
import com.omgservers.module.internal.factory.LogModelFactory;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.executeChange.ExecuteChangeOperation;
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

    final ExecuteChangeOperation executeChangeOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> upsertProjectPermission(final ChangeContext<?> changeContext,
                                                final SqlConnection sqlConnection,
                                                final int shard,
                                                final Long tenantId,
                                                final ProjectPermissionModel permission) {
        return executeChangeOperation.executeChange(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_tenant_project_permission(id, project_id, created, user_id, permission)
                        values($1, $2, $3, $4, $5)
                        on conflict (id) do
                        nothing
                        """,
                Arrays.asList(
                        permission.getId(),
                        permission.getProjectId(),
                        permission.getCreated().atOffset(ZoneOffset.UTC),
                        permission.getUserId(),
                        permission.getPermission()
                ),
                () -> null,
                () -> logModelFactory.create(String.format("Project permission was inserted, " +
                        "tenantId=%d, permission=%s", tenantId, permission))
        );
    }
}
