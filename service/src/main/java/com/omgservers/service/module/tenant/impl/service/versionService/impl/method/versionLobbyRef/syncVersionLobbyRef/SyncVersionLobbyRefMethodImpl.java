package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionLobbyRef.syncVersionLobbyRef;

import com.omgservers.schema.module.tenant.SyncVersionLobbyRefRequest;
import com.omgservers.schema.module.tenant.SyncVersionLobbyRefResponse;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.module.tenant.impl.operation.version.hasVersion.HasVersionOperation;
import com.omgservers.service.module.tenant.impl.operation.versionLobbyRef.upsertVersionLobbyRef.UpsertVersionLobbyRefOperation;
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
class SyncVersionLobbyRefMethodImpl implements SyncVersionLobbyRefMethod {

    final UpsertVersionLobbyRefOperation upsertVersionLobbyRefOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;
    final HasVersionOperation hasVersionOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncVersionLobbyRefResponse> syncVersionLobbyRef(final SyncVersionLobbyRefRequest request) {
        log.debug("Sync version lobby ref, request={}", request);

        final var shardKey = request.getRequestShardKey();
        final var versionLobbyRef = request.getVersionLobbyRef();
        final var tenantId = versionLobbyRef.getTenantId();
        final var versionId = versionLobbyRef.getVersionId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(shardKey))
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (changeContext, sqlConnection) -> hasVersionOperation
                                            .hasVersion(sqlConnection, shard, tenantId, versionId)
                                            .flatMap(has -> {
                                                if (has) {
                                                    return upsertVersionLobbyRefOperation
                                                            .upsertVersionLobbyRef(changeContext,
                                                                    sqlConnection,
                                                                    shardModel.shard(),
                                                                    versionLobbyRef);
                                                } else {
                                                    throw new ServerSideNotFoundException(
                                                            ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                            "version does not exist or was deleted, id=" + versionId);
                                                }
                                            })

                            )
                            .map(ChangeContext::getResult);
                })
                .map(SyncVersionLobbyRefResponse::new);
    }
}
