package com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeCommand;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.module.runtime.runtimeCommand.SyncRuntimeCommandRequest;
import com.omgservers.schema.module.runtime.runtimeCommand.SyncRuntimeCommandResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import com.omgservers.service.shard.runtime.impl.operation.runtime.VerifyRuntimeExistsOperation;
import com.omgservers.service.shard.runtime.impl.operation.runtimeCommand.UpsertRuntimeCommandOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncRuntimeCommandMethodImpl implements SyncRuntimeCommandMethod {

    final UpsertRuntimeCommandOperation upsertRuntimeCommandOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;
    final VerifyRuntimeExistsOperation verifyRuntimeExistsOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncRuntimeCommandResponse> execute(final SyncRuntimeCommandRequest request) {
        log.trace("{}", request);

        final var shardKey = request.getRequestShardKey();
        final var runtimeCommand = request.getRuntimeCommand();
        final var runtimeId = runtimeCommand.getRuntimeId();

        return checkShardOperation.checkShard(shardKey)
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (changeContext, sqlConnection) -> verifyRuntimeExistsOperation
                                            .execute(sqlConnection, shard, runtimeId)
                                            .flatMap(exists -> {
                                                if (exists) {
                                                    return upsertRuntimeCommandOperation.execute(
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
                .map(SyncRuntimeCommandResponse::new);
    }
}
