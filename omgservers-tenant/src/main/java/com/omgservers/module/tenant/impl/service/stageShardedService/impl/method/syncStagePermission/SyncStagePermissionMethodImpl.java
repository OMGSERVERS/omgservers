package com.omgservers.module.tenant.impl.service.stageShardedService.impl.method.syncStagePermission;

import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.dto.tenant.SyncStagePermissionShardedRequest;
import com.omgservers.dto.tenant.SyncStagePermissionShardedResponse;
import com.omgservers.model.shard.ShardModel;
import com.omgservers.model.stagePermission.StagePermissionModel;
import com.omgservers.module.tenant.impl.operation.upsertStagePermission.UpsertStagePermissionOperation;
import com.omgservers.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncStagePermissionMethodImpl implements SyncStagePermissionMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final UpsertStagePermissionOperation upsertStagePermissionOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncStagePermissionShardedResponse> syncStagePermission(final SyncStagePermissionShardedRequest request) {
        SyncStagePermissionShardedRequest.validate(request);

        final var tenantId = request.getTenantId();
        final var permission = request.getPermission();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeFunction(shardModel, tenantId, permission))
                .map(SyncStagePermissionShardedResponse::new);
    }

    Uni<Boolean> changeFunction(ShardModel shardModel, Long tenantId, StagePermissionModel permission) {
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        upsertStagePermissionOperation.upsertStagePermission(
                                changeContext,
                                sqlConnection,
                                shardModel.shard(),
                                tenantId,
                                permission))
                .map(ChangeContext::getResult);
    }
}
