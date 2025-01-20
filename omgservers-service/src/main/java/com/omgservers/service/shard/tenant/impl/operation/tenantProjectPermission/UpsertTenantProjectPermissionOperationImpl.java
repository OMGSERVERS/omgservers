package com.omgservers.service.shard.tenant.impl.operation.tenantProjectPermission;

import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionModel;
import com.omgservers.service.factory.lobby.LogModelFactory;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeObjectOperation;
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
class UpsertTenantProjectPermissionOperationImpl implements UpsertTenantProjectPermissionOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int shard,
                                final TenantProjectPermissionModel tenantProjectPermission) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_tenant_project_permission(
                            id, idempotency_key, tenant_id, project_id, created, modified, user_id, permission, deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8, $9)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        tenantProjectPermission.getId(),
                        tenantProjectPermission.getIdempotencyKey(),
                        tenantProjectPermission.getTenantId(),
                        tenantProjectPermission.getProjectId(),
                        tenantProjectPermission.getCreated().atOffset(ZoneOffset.UTC),
                        tenantProjectPermission.getModified().atOffset(ZoneOffset.UTC),
                        tenantProjectPermission.getUserId(),
                        tenantProjectPermission.getPermission(),
                        tenantProjectPermission.getDeleted()
                ),
                () -> null,
                () -> null
        );
    }
}
