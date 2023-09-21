package com.omgservers.module.runtime.impl.service.runtimeService.impl.method.syncRuntimeGrant;

import com.omgservers.dto.runtime.SyncRuntimeGrantRequest;
import com.omgservers.dto.runtime.SyncRuntimeGrantResponse;
import com.omgservers.module.runtime.impl.operation.upsertRuntimeGrant.UpsertRuntimeGrantOperation;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
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

    @Override
    public Uni<SyncRuntimeGrantResponse> syncRuntimeGrant(SyncRuntimeGrantRequest request) {
        final var runtimeGrant = request.getRuntimeGrant();

        log.info("Sync runtime grant, runtimeId={}, type={}, entityId={}",
                runtimeGrant.getRuntimeId(), runtimeGrant.getType(), runtimeGrant.getEntityId());

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) ->
                                        upsertRuntimeGrantOperation.upsertRuntimeGrant(
                                                changeContext,
                                                sqlConnection,
                                                shardModel.shard(),
                                                runtimeGrant))
                        .map(ChangeContext::getResult))
                .map(SyncRuntimeGrantResponse::new);
    }
}
