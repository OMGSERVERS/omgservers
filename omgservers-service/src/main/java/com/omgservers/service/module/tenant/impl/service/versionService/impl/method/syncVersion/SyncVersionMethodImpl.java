package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.syncVersion;

import com.omgservers.model.dto.tenant.SyncVersionRequest;
import com.omgservers.model.dto.tenant.SyncVersionResponse;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.module.tenant.impl.operation.hasStage.HasStageOperation;
import com.omgservers.service.module.tenant.impl.operation.upsertVersion.UpsertVersionOperation;
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
class SyncVersionMethodImpl implements SyncVersionMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final UpsertVersionOperation upsertVersionOperation;
    final CheckShardOperation checkShardOperation;
    final HasStageOperation hasStageOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncVersionResponse> syncVersion(final SyncVersionRequest request) {
        final var shardKey = request.getRequestShardKey();
        final var version = request.getVersion();
        final var tenantId = version.getTenantId();
        final var stageId = version.getStageId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(shardKey))
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (changeContext, sqlConnection) -> hasStageOperation
                                            .hasStage(sqlConnection, shard, tenantId, stageId)
                                            .flatMap(has -> {
                                                if (has) {
                                                    return upsertVersionOperation.upsertVersion(changeContext,
                                                            sqlConnection,
                                                            shard,
                                                            version);
                                                } else {
                                                    throw new ServerSideConflictException(
                                                            "stage does not exist or was deleted, id=" + stageId);
                                                }
                                            })
                            )
                            .map(ChangeContext::getResult);
                })
                .map(SyncVersionResponse::new);
    }
}
