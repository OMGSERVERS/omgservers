package com.omgservers.service.shard.tenant.impl.operation.tenantStagePermission;

import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionModel;
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
class UpsertTenantStagePermissionOperationImpl implements UpsertTenantStagePermissionOperation {

    final ChangeObjectOperation changeObjectOperation;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int slot,
                                final TenantStagePermissionModel tenantStagePermission) {
        return changeObjectOperation.execute(
                changeContext, sqlConnection, slot,
                """
                        insert into $slot.tab_tenant_stage_permission(
                            id, idempotency_key, tenant_id, stage_id, created, modified, user_id, permission, deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8, $9)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        tenantStagePermission.getId(),
                        tenantStagePermission.getIdempotencyKey(),
                        tenantStagePermission.getTenantId(),
                        tenantStagePermission.getStageId(),
                        tenantStagePermission.getCreated().atOffset(ZoneOffset.UTC),
                        tenantStagePermission.getModified().atOffset(ZoneOffset.UTC),
                        tenantStagePermission.getUserId(),
                        tenantStagePermission.getPermission(),
                        tenantStagePermission.getDeleted()
                ),
                () -> null,
                () -> null
        );
    }
}
