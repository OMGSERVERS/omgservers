package com.omgservers.service.module.tenant.impl.service.stageService.impl.method.syncStage;

import com.omgservers.model.dto.tenant.SyncStageRequest;
import com.omgservers.model.dto.tenant.SyncStageResponse;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.module.tenant.impl.operation.hasProject.HasProjectOperation;
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
    final HasProjectOperation hasProjectOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncStageResponse> syncStage(final SyncStageRequest request) {
        final var shardKey = request.getRequestShardKey();
        final var stage = request.getStage();
        final var tenantId = stage.getTenantId();
        final var projectId = stage.getProjectId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(shardKey))
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (changeContext, sqlConnection) -> hasProjectOperation
                                            .hasProject(sqlConnection, shard, tenantId, projectId)
                                            .flatMap(has -> {
                                                if (has) {
                                                    return upsertStageOperation.upsertStage(changeContext,
                                                            sqlConnection,
                                                            shard,
                                                            stage);
                                                } else {
                                                    throw new ServerSideConflictException(
                                                            "project does not exist or was deleted, id=" + projectId);
                                                }
                                            })
                            )
                            .map(ChangeContext::getResult);
                })
                .map(SyncStageResponse::new);
    }
}
