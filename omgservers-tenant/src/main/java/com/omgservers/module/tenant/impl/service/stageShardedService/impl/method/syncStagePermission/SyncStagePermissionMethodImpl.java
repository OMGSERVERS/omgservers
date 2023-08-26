package com.omgservers.module.tenant.impl.service.stageShardedService.impl.method.syncStagePermission;

import com.omgservers.dto.tenantModule.SyncStagePermissionShardRequest;
import com.omgservers.module.tenant.impl.operation.upsertStagePermission.UpsertStagePermissionOperation;
import com.omgservers.module.internal.impl.factory.LogModelFactory;
import com.omgservers.module.internal.InternalModule;
import com.omgservers.dto.internalModule.ChangeWithLogRequest;
import com.omgservers.dto.internalModule.ChangeWithLogResponse;
import com.omgservers.dto.tenantModule.SyncStagePermissionInternalResponse;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncStagePermissionMethodImpl implements SyncStagePermissionMethod {

    final InternalModule internalModule;

    final UpsertStagePermissionOperation upsertStagePermissionOperation;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<SyncStagePermissionInternalResponse> syncStagePermission(final SyncStagePermissionShardRequest request) {
        SyncStagePermissionShardRequest.validate(request);

        final var tenantId = request.getTenantId();
        final var permission = request.getPermission();
        return internalModule.getChangeService().changeWithLog(new ChangeWithLogRequest(request,
                        (sqlConnection, shardModel) -> upsertStagePermissionOperation
                                .upsertStagePermission(sqlConnection, shardModel.shard(), permission),
                        inserted -> {
                            if (inserted) {
                                return logModelFactory.create(String.format("Stage permission was created, " +
                                        "tenantId=%d, permission=%s", tenantId, permission));
                            } else {
                                return logModelFactory.create(String.format("Stage permission was updated, " +
                                        "tenantId=%d, permission=%s", tenantId, permission));
                            }
                        }))
                .map(ChangeWithLogResponse::getResult)
                .map(SyncStagePermissionInternalResponse::new);
    }
}
