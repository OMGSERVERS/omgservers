package com.omgservers.application.module.tenantModule.impl.service.stageInternalService.impl.method.syncStagePermissionMethod;

import com.omgservers.application.module.internalModule.InternalModule;
import com.omgservers.application.module.internalModule.impl.service.logHelpService.request.SyncLogHelpRequest;
import com.omgservers.application.module.internalModule.model.log.LogModel;
import com.omgservers.application.module.internalModule.model.log.LogModelFactory;
import com.omgservers.application.module.tenantModule.impl.operation.upsertStagePermissionOperation.UpsertStagePermissionOperation;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.request.SyncStagePermissionInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.response.SyncStagePermissionInternalResponse;
import com.omgservers.application.operation.checkShardOperation.CheckShardOperation;
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
    final CheckShardOperation checkShardOperation;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<SyncStagePermissionInternalResponse> syncStagePermission(final SyncStagePermissionInternalRequest request) {
        SyncStagePermissionInternalRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var permission = request.getPermission();
                    return pgPool.withTransaction(sqlConnection -> upsertStagePermissionOperation
                            .upsertStagePermission(sqlConnection, shard.shard(), permission)
                            .call(inserted -> {
                                final LogModel syncLog;
                                if (inserted) {
                                    syncLog = logModelFactory.create("Stage permission was created, " +
                                            "permission=" + permission);
                                } else {
                                    syncLog = logModelFactory.create("Stage permission was updated, " +
                                            "permission=" + permission);
                                }
                                final var syncLogHelpRequest = new SyncLogHelpRequest(syncLog);
                                return internalModule.getLogHelpService().syncLog(syncLogHelpRequest);
                            }));
                })
                .map(SyncStagePermissionInternalResponse::new);
    }
}
