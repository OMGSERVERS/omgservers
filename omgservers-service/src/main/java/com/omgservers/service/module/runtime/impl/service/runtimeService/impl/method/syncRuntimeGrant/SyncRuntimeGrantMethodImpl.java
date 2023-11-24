package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.syncRuntimeGrant;

import com.omgservers.model.dto.runtime.SyncRuntimeGrantRequest;
import com.omgservers.model.dto.runtime.SyncRuntimeGrantResponse;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.module.runtime.impl.operation.hasRuntime.HasRuntimeOperation;
import com.omgservers.service.module.runtime.impl.operation.upsertRuntimeGrant.UpsertRuntimeGrantOperation;
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
class SyncRuntimeGrantMethodImpl implements SyncRuntimeGrantMethod {

    final UpsertRuntimeGrantOperation upsertRuntimeGrantOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;
    final HasRuntimeOperation hasRuntimeOperation;

    @Override
    public Uni<SyncRuntimeGrantResponse> syncRuntimeGrant(final SyncRuntimeGrantRequest request) {
        log.debug("Sync runtime grant, request={}", request);

        final var shardKey = request.getRequestShardKey();
        final var runtimeGrant = request.getRuntimeGrant();
        final var runtimeId = runtimeGrant.getRuntimeId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(shardKey))
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (changeContext, sqlConnection) -> hasRuntimeOperation
                                            .hasRuntime(sqlConnection, shard, runtimeId)
                                            .flatMap(has -> {
                                                if (has) {
                                                    return upsertRuntimeGrantOperation.upsertRuntimeGrant(
                                                            changeContext,
                                                            sqlConnection,
                                                            shardModel.shard(),
                                                            runtimeGrant);
                                                } else {
                                                    throw new ServerSideConflictException(
                                                            "runtime does not exist or was deleted, id=" + runtimeId);
                                                }
                                            })
                            )
                            .map(ChangeContext::getResult);
                })
                .map(SyncRuntimeGrantResponse::new);
    }
}
