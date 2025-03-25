package com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeMessage;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.module.runtime.runtimeMessage.SyncRuntimeMessageRequest;
import com.omgservers.schema.module.runtime.runtimeMessage.SyncRuntimeMessageResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import com.omgservers.service.shard.runtime.impl.operation.runtime.VerifyRuntimeExistsOperation;
import com.omgservers.service.shard.runtime.impl.operation.runtimeMessage.UpsertRuntimeMessageOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncRuntimeMessageMethodImpl implements SyncRuntimeMessageMethod {

    final UpsertRuntimeMessageOperation upsertRuntimeMessageOperation;
    final VerifyRuntimeExistsOperation verifyRuntimeExistsOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncRuntimeMessageResponse> execute(final SyncRuntimeMessageRequest request) {
        log.trace("{}", request);

        final var shardKey = request.getRequestShardKey();
        final var runtimeMessage = request.getRuntimeMessage();
        final var runtimeId = runtimeMessage.getRuntimeId();

        return checkShardOperation.checkShard(shardKey)
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (changeContext, sqlConnection) -> verifyRuntimeExistsOperation
                                            .execute(sqlConnection, shard, runtimeId)
                                            .flatMap(has -> {
                                                if (has) {
                                                    return upsertRuntimeMessageOperation.execute(
                                                            changeContext,
                                                            sqlConnection,
                                                            shard,
                                                            runtimeMessage);
                                                } else {
                                                    throw new ServerSideNotFoundException(
                                                            ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                            "runtime does not exist or was deleted, id=" + runtimeId);
                                                }
                                            }))
                            .map(ChangeContext::getResult);
                })
                .map(SyncRuntimeMessageResponse::new);
    }
}
