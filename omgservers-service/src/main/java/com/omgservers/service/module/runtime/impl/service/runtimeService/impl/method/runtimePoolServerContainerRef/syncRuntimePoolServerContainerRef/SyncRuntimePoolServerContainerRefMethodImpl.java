package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimePoolServerContainerRef.syncRuntimePoolServerContainerRef;

import com.omgservers.model.dto.runtime.poolServerContainerRef.SyncRuntimePoolServerContainerRefRequest;
import com.omgservers.model.dto.runtime.poolServerContainerRef.SyncRuntimePoolServerContainerRefResponse;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.module.runtime.impl.operation.runtime.hasRuntime.HasRuntimeOperation;
import com.omgservers.service.module.runtime.impl.operation.runtimePoolServerContainerRef.upsertRuntimePoolServerContainerRef.UpsertRuntimePoolServerContainerRefOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncRuntimePoolServerContainerRefMethodImpl implements SyncRuntimePoolServerContainerRefMethod {

    final UpsertRuntimePoolServerContainerRefOperation upsertRuntimePoolServerContainerRefOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;
    final HasRuntimeOperation hasRuntimeOperation;

    @Override
    public Uni<SyncRuntimePoolServerContainerRefResponse> syncRuntimePoolServerContainerRef(
            final SyncRuntimePoolServerContainerRefRequest request) {
        log.debug("Sync runtime pool server container ref, request={}", request);

        final var shardKey = request.getRequestShardKey();
        final var runtimeServerContainerRef = request.getRuntimePoolServerContainerRef();
        final var runtimeId = runtimeServerContainerRef.getRuntimeId();

        return checkShardOperation.checkShard(shardKey)
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (changeContext, sqlConnection) -> hasRuntimeOperation
                                            .hasRuntime(sqlConnection, shard, runtimeId)
                                            .flatMap(has -> {
                                                if (has) {
                                                    return upsertRuntimePoolServerContainerRefOperation
                                                            .upsertRuntimePoolServerContainerRef(changeContext,
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
                .map(SyncRuntimePoolServerContainerRefResponse::new);
    }
}
