package com.omgservers.service.module.tenant.impl.service.stageService.impl.method.syncStage;

import com.omgservers.model.dto.tenant.SyncStageRequest;
import com.omgservers.model.dto.tenant.SyncStageResponse;
import com.omgservers.service.module.tenant.impl.operation.upsertStage.UpsertStageOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
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
    final UpsertStageOperation upsertStageOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncStageResponse> syncStage(final SyncStageRequest request) {
        final var tenantId = request.getTenantId();
        final var stage = request.getStage();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) -> upsertStageOperation
                                        .upsertStage(changeContext,
                                                sqlConnection,
                                                shardModel.shard(),
                                                stage))
                        .map(ChangeContext::getResult)
                )
                .map(SyncStageResponse::new);
    }
}
