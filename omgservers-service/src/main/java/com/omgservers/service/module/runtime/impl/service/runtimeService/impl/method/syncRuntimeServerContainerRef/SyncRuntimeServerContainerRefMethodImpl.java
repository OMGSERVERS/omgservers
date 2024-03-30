package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.syncRuntimeServerContainerRef;

import com.omgservers.model.dto.runtime.serverRuntimeRef.SyncRuntimeServerContainerRefRequest;
import com.omgservers.model.dto.runtime.serverRuntimeRef.SyncRuntimeServerContainerRefResponse;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.module.runtime.impl.operation.runtime.hasRuntime.HasRuntimeOperation;
import com.omgservers.service.module.runtime.impl.operation.runtimeServerContainerRef.upsertRuntimeServerContainerRef.UpsertRuntimeServerContainerRefOperation;
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
class SyncRuntimeServerContainerRefMethodImpl implements SyncRuntimeServerContainerRefMethod {

    final UpsertRuntimeServerContainerRefOperation upsertRuntimeServerContainerRefOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;
    final HasRuntimeOperation hasRuntimeOperation;

    @Override
    public Uni<SyncRuntimeServerContainerRefResponse> syncRuntimeServerContainerRef(
            final SyncRuntimeServerContainerRefRequest request) {
        log.debug("Sync runtime server container ref, request={}", request);

        final var shardKey = request.getRequestShardKey();
        final var runtimeServerContainerRef = request.getRuntimeServerContainerRef();
        final var runtimeId = runtimeServerContainerRef.getRuntimeId();

        return checkShardOperation.checkShard(shardKey)
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (changeContext, sqlConnection) -> hasRuntimeOperation
                                            .hasRuntime(sqlConnection, shard, runtimeId)
                                            .flatMap(has -> {
                                                if (has) {
                                                    return upsertRuntimeServerContainerRefOperation
                                                            .upsertRuntimeServerContainerRef(changeContext,
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
                .map(SyncRuntimeServerContainerRefResponse::new);
    }
}
