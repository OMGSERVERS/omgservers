package com.omgservers.module.tenant.impl.service.stageShardedService.impl.method.syncStage;

import com.omgservers.dto.tenant.SyncStageShardedRequest;
import com.omgservers.dto.tenant.SyncStageShardedResponse;
import com.omgservers.model.shard.ShardModel;
import com.omgservers.model.stage.StageModel;
import com.omgservers.module.tenant.impl.operation.upsertStage.UpsertStageOperation;
import com.omgservers.module.tenant.impl.operation.validateStage.ValidateStageOperation;
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
class SyncStageMethodImpl implements SyncStageMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final ValidateStageOperation validateStageOperation;
    final UpsertStageOperation upsertStageOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncStageShardedResponse> syncStage(SyncStageShardedRequest request) {
        SyncStageShardedRequest.validate(request);

        final var tenantId = request.getTenantId();
        final var stage = request.getStage();
        validateStageOperation.validateStage(stage);

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeFunction(shardModel, tenantId, stage))
                .map(SyncStageShardedResponse::new);
    }

    Uni<Boolean> changeFunction(ShardModel shardModel, Long tenantId, StageModel stage) {
        return changeWithContextOperation.changeWithContext((changeContext, sqlConnection) ->
                upsertStageOperation.upsertStage(changeContext, sqlConnection, shardModel.shard(), tenantId, stage));
    }
}
