package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionMatchmakerRef.syncVersionMatchmakerRef;

import com.omgservers.schema.module.tenant.SyncVersionMatchmakerRefRequest;
import com.omgservers.schema.module.tenant.SyncVersionMatchmakerRefResponse;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.module.tenant.impl.operation.version.hasVersion.HasVersionOperation;
import com.omgservers.service.module.tenant.impl.operation.versionMatchmakerRef.upsertVersionMatchmakerRef.UpsertVersionMatchmakerRefOperation;
import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
import com.omgservers.service.server.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.service.server.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncVersionMatchmakerRefMethodImpl implements SyncVersionMatchmakerRefMethod {

    final UpsertVersionMatchmakerRefOperation upsertVersionMatchmakerRefOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;
    final HasVersionOperation hasVersionOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncVersionMatchmakerRefResponse> syncVersionMatchmakerRef(
            final SyncVersionMatchmakerRefRequest request) {
        log.debug("Sync version matchmaker ref, request={}", request);

        final var shardKey = request.getRequestShardKey();
        final var versionMatchmakerRef = request.getVersionMatchmakerRef();
        final var tenantId = versionMatchmakerRef.getTenantId();
        final var versionId = versionMatchmakerRef.getVersionId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(shardKey))
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (changeContext, sqlConnection) -> hasVersionOperation
                                            .hasVersion(sqlConnection, shard, tenantId, versionId)
                                            .flatMap(has -> {
                                                if (has) {
                                                    return upsertVersionMatchmakerRefOperation
                                                            .upsertVersionMatchmakerRef(
                                                                    changeContext,
                                                                    sqlConnection,
                                                                    shard,
                                                                    versionMatchmakerRef);
                                                } else {
                                                    throw new ServerSideNotFoundException(
                                                            ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                            "version does not exist or was deleted, id=" + versionId);
                                                }
                                            })
                            )
                            .map(ChangeContext::getResult);
                })
                .map(SyncVersionMatchmakerRefResponse::new);
    }
}
