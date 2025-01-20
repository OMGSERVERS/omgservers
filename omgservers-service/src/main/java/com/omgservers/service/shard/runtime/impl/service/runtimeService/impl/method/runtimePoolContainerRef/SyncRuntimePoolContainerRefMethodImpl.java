package com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimePoolContainerRef;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.module.runtime.poolContainerRef.SyncRuntimePoolContainerRefRequest;
import com.omgservers.schema.module.runtime.poolContainerRef.SyncRuntimePoolContainerRefResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.shard.runtime.impl.operation.runtime.HasRuntimeOperation;
import com.omgservers.service.shard.runtime.impl.operation.runtimePoolContainerRef.UpsertRuntimePoolContainerRefOperation;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncRuntimePoolContainerRefMethodImpl implements SyncRuntimePoolContainerRefMethod {

    final UpsertRuntimePoolContainerRefOperation upsertRuntimePoolContainerRefOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;
    final HasRuntimeOperation hasRuntimeOperation;

    @Override
    public Uni<SyncRuntimePoolContainerRefResponse> execute(
            final SyncRuntimePoolContainerRefRequest request) {
        log.trace("{}", request);

        final var shardKey = request.getRequestShardKey();
        final var runtimeServerContainerRef = request.getRuntimePoolContainerRef();
        final var runtimeId = runtimeServerContainerRef.getRuntimeId();

        return checkShardOperation.checkShard(shardKey)
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (changeContext, sqlConnection) -> hasRuntimeOperation
                                            .execute(sqlConnection, shard, runtimeId)
                                            .flatMap(has -> {
                                                if (has) {
                                                    return upsertRuntimePoolContainerRefOperation
                                                            .execute(changeContext,
                                                                    sqlConnection,
                                                                    shardModel.shard(),
                                                                    runtimeServerContainerRef);
                                                } else {
                                                    throw new ServerSideNotFoundException(
                                                            ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                            "runtime does not exist or was deleted, id=" + runtimeId);
                                                }
                                            })
                            )
                            .map(ChangeContext::getResult);
                })
                .map(SyncRuntimePoolContainerRefResponse::new);
    }
}
