package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.syncRuntimeGrant;

import com.omgservers.model.dto.runtime.SyncRuntimeGrantRequest;
import com.omgservers.model.dto.runtime.SyncRuntimeGrantResponse;
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

    @Override
    public Uni<SyncRuntimeGrantResponse> syncRuntimeGrant(SyncRuntimeGrantRequest request) {
        final var runtimeGrant = request.getRuntimeGrant();

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
