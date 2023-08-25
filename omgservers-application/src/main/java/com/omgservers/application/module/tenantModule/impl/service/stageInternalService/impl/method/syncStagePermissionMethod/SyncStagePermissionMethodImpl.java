package com.omgservers.application.module.tenantModule.impl.service.stageInternalService.impl.method.syncStagePermissionMethod;

import com.omgservers.base.factory.LogModelFactory;
import com.omgservers.base.InternalModule;
import com.omgservers.application.module.tenantModule.impl.operation.upsertStagePermissionOperation.UpsertStagePermissionOperation;
import com.omgservers.base.impl.operation.changeOperation.ChangeOperation;
import com.omgservers.dto.tenantModule.SyncStagePermissionInternalRequest;
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
