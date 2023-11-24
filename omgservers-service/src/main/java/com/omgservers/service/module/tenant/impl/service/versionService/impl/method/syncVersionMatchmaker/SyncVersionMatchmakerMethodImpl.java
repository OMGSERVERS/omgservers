package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.syncVersionMatchmaker;

import com.omgservers.model.dto.tenant.SyncVersionMatchmakerRequest;
import com.omgservers.model.dto.tenant.SyncVersionMatchmakerResponse;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.module.tenant.impl.operation.hasVersion.HasVersionOperation;
import com.omgservers.service.module.tenant.impl.operation.upsertVersionMatchmaker.UpsertVersionMatchmakerOperation;
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
class SyncVersionMatchmakerMethodImpl implements SyncVersionMatchmakerMethod {

    final UpsertVersionMatchmakerOperation upsertVersionMatchmakerOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;
    final HasVersionOperation hasVersionOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncVersionMatchmakerResponse> syncVersionMatchmaker(final SyncVersionMatchmakerRequest request) {
        log.debug("Sync version matchmaker, request={}", request);
        final var shardKey = request.getRequestShardKey();
        final var versionMatchmaker = request.getVersionMatchmaker();
        final var tenantId = versionMatchmaker.getTenantId();
        final var versionId = versionMatchmaker.getVersionId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(shardKey))
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (changeContext, sqlConnection) -> hasVersionOperation
                                            .hasVersion(sqlConnection, shard, tenantId, versionId)
                                            .flatMap(has -> {
                                                if (has) {
                                                    return upsertVersionMatchmakerOperation.upsertVersionMatchmaker(
                                                            changeContext,
                                                            sqlConnection,
                                                            shard,
                                                            versionMatchmaker);
                                                } else {
                                                    throw new ServerSideConflictException(
                                                            "version does not exist or was deleted, id=" + versionId);
                                                }
                                            })
                            )
                            .map(ChangeContext::getResult);
                })
                .map(SyncVersionMatchmakerResponse::new);
    }
}
