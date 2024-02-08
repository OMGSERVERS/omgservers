package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.syncClientCommand;

import com.omgservers.model.dto.runtime.SyncClientCommandRequest;
import com.omgservers.model.dto.runtime.SyncClientCommandResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.module.runtime.impl.operation.hasRuntime.HasRuntimeOperation;
import com.omgservers.service.module.runtime.impl.operation.updateRuntimeClientLastActivity.UpdateRuntimeClientLastActivityOperation;
import com.omgservers.service.module.runtime.impl.operation.upsertRuntimeCommand.UpsertRuntimeCommandOperation;
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
class SyncClientCommandMethodImpl implements SyncClientCommandMethod {

    final UpdateRuntimeClientLastActivityOperation updateRuntimeClientLastActivityOperation;
    final UpsertRuntimeCommandOperation upsertRuntimeCommandOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;
    final HasRuntimeOperation hasRuntimeOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncClientCommandResponse> syncClientCommand(final SyncClientCommandRequest request) {
        log.debug("Sync client command, request={}", request);

        final var shardKey = request.getRequestShardKey();
        final var runtimeCommand = request.getRuntimeCommand();
        final var runtimeId = runtimeCommand.getRuntimeId();
        final var clientId = request.getClientId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(shardKey))
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (changeContext, sqlConnection) -> hasRuntimeOperation
                                            .hasRuntime(sqlConnection, shard, runtimeId)
                                            .flatMap(has -> {
                                                if (has) {
                                                    return updateRuntimeClientLastActivityOperation
                                                            .updateRuntimeClientLastActivity(
                                                                    changeContext,
                                                                    sqlConnection,
                                                                    shard,
                                                                    runtimeId,
                                                                    clientId)
                                                            .flatMap(updated -> upsertRuntimeCommandOperation
                                                                    .upsertRuntimeCommand(
                                                                            changeContext,
                                                                            sqlConnection,
                                                                            shard,
                                                                            runtimeCommand));
                                                } else {
                                                    throw new ServerSideNotFoundException(
                                                            "runtime does not exist or was deleted, id=" + runtimeId);
                                                }
                                            })
                            )
                            .map(ChangeContext::getResult);
                })
                .map(SyncClientCommandResponse::new);
    }
}