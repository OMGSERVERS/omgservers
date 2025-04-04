package com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeCommand;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.module.runtime.SyncClientCommandRequest;
import com.omgservers.schema.module.runtime.SyncClientCommandResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.shard.runtime.impl.operation.runtime.HasRuntimeOperation;
import com.omgservers.service.shard.runtime.impl.operation.runtimeCommand.UpsertRuntimeCommandOperation;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncClientCommandMethodImpl implements SyncClientCommandMethod {

    final UpsertRuntimeCommandOperation upsertRuntimeCommandOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;
    final HasRuntimeOperation hasRuntimeOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncClientCommandResponse> execute(final SyncClientCommandRequest request) {
        log.trace("{}", request);

        final var shardKey = request.getRequestShardKey();
        final var runtimeCommand = request.getRuntimeCommand();
        final var runtimeId = runtimeCommand.getRuntimeId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(shardKey))
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (changeContext, sqlConnection) -> hasRuntimeOperation
                                            .execute(sqlConnection, shard, runtimeId)
                                            .flatMap(has -> {
                                                if (has) {
                                                    return upsertRuntimeCommandOperation
                                                            .execute(
                                                                    changeContext,
                                                                    sqlConnection,
                                                                    shard,
                                                                    runtimeCommand);
                                                } else {
                                                    throw new ServerSideNotFoundException(
                                                            ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                            "runtime does not exist or was deleted, id=" + runtimeId);
                                                }
                                            })
                            )
                            .map(ChangeContext::getResult);
                })
                .map(SyncClientCommandResponse::new);
    }
}
