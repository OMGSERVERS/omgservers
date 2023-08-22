package com.omgservers.application.module.tenantModule.impl.service.stageInternalService.impl.method.syncStagePermissionMethod;

import com.omgservers.application.module.internalModule.InternalModule;
import com.omgservers.application.module.internalModule.model.log.LogModelFactory;
import com.omgservers.application.module.tenantModule.impl.operation.upsertStagePermissionOperation.UpsertStagePermissionOperation;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.request.SyncStagePermissionInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.response.SyncStagePermissionInternalResponse;
import com.omgservers.application.operation.changeOperation.ChangeOperation;
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
    final ChangeOperation changeOperation;

    final LogModelFactory logModelFactory;
    final PgPool pgPool;

    @Override
    public Uni<SyncStagePermissionInternalResponse> syncStagePermission(final SyncStagePermissionInternalRequest request) {
        SyncStagePermissionInternalRequest.validate(request);

        final var tenantId = request.getTenantId();
        final var permission = request.getPermission();
        return changeOperation.changeWithLog(request,
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
                        })
                .map(SyncStagePermissionInternalResponse::new);
    }
}
