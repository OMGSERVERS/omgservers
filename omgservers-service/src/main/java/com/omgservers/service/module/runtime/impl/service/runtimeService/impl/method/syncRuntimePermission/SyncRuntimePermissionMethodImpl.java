package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.syncRuntimePermission;

import com.omgservers.model.dto.runtime.SyncRuntimePermissionRequest;
import com.omgservers.model.dto.runtime.SyncRuntimePermissionResponse;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.module.runtime.impl.operation.hasRuntime.HasRuntimeOperation;
import com.omgservers.service.module.runtime.impl.operation.upsertRuntimePermission.UpsertRuntimePermissionOperation;
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
class SyncRuntimePermissionMethodImpl implements SyncRuntimePermissionMethod {

    final UpsertRuntimePermissionOperation upsertRuntimePermissionOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;
    final HasRuntimeOperation hasRuntimeOperation;

    @Override
    public Uni<SyncRuntimePermissionResponse> syncRuntimePermission(final SyncRuntimePermissionRequest request) {
        log.debug("Sync runtime permission, request={}", request);

        final var shardKey = request.getRequestShardKey();
        final var runtimePermission = request.getRuntimePermission();
        final var runtimeId = runtimePermission.getRuntimeId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(shardKey))
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext(
                                    (changeContext, sqlConnection) -> hasRuntimeOperation
                                            .hasRuntime(sqlConnection, shard, runtimeId)
                                            .flatMap(has -> {
                                                if (has) {
                                                    return upsertRuntimePermissionOperation.upsertRuntimePermission(
                                                            changeContext,
                                                            sqlConnection,
                                                            shardModel.shard(),
                                                            runtimePermission);
                                                } else {
                                                    throw new ServerSideConflictException(
                                                            "runtime does not exist or was deleted, id=" + runtimeId);
                                                }
                                            })
                            )
                            .map(ChangeContext::getResult);
                })
                .map(SyncRuntimePermissionResponse::new);
    }
}
