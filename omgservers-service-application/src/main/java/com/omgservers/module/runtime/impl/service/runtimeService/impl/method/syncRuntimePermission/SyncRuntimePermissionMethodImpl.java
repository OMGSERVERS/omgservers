package com.omgservers.module.runtime.impl.service.runtimeService.impl.method.syncRuntimePermission;

import com.omgservers.model.dto.runtime.SyncRuntimePermissionRequest;
import com.omgservers.model.dto.runtime.SyncRuntimePermissionResponse;
import com.omgservers.module.runtime.impl.operation.upsertRuntimePermission.UpsertRuntimePermissionOperation;
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
class SyncRuntimePermissionMethodImpl implements SyncRuntimePermissionMethod {

    final UpsertRuntimePermissionOperation upsertRuntimePermissionOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<SyncRuntimePermissionResponse> syncRuntimePermission(final SyncRuntimePermissionRequest request) {
        final var runtimePermission = request.getRuntimePermission();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) ->
                                        upsertRuntimePermissionOperation.upsertRuntimePermission(
                                                changeContext,
                                                sqlConnection,
                                                shardModel.shard(),
                                                runtimePermission))
                        .map(ChangeContext::getResult))
                .map(SyncRuntimePermissionResponse::new);
    }
}
