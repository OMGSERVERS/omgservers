package com.omgservers.service.module.tenant.impl.operation.upsertStagePermission;

import com.omgservers.model.stagePermission.StagePermissionModel;
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
class UpsertStagePermissionOperationImpl implements UpsertStagePermissionOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> upsertStagePermission(final ChangeContext<?> changeContext,
                                              final SqlConnection sqlConnection,
                                              final int shard,
                                              final StagePermissionModel stagePermission) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_tenant_stage_permission(
                            id, idempotency_key, tenant_id, stage_id, created, modified, user_id, permission, deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8, $9)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        stagePermission.getId(),
                        stagePermission.getIdempotencyKey(),
                        stagePermission.getTenantId(),
                        stagePermission.getStageId(),
                        stagePermission.getCreated().atOffset(ZoneOffset.UTC),
                        stagePermission.getModified().atOffset(ZoneOffset.UTC),
                        stagePermission.getUserId(),
                        stagePermission.getPermission(),
                        stagePermission.getDeleted()
                ),
                () -> null,
                () -> null
        );
    }
}
