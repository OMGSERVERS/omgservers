package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimePoolServerContainerRef.deleteRuntimePoolServerContainerRef;

import com.omgservers.schema.module.runtime.poolServerContainerRef.DeleteRuntimePoolServerContainerRefRequest;
import com.omgservers.schema.module.runtime.poolServerContainerRef.DeleteRuntimePoolServerContainerRefResponse;
import com.omgservers.service.module.runtime.impl.operation.runtimePoolServerContainerRef.deleteRuntimePoolServerContainerRef.DeleteRuntimePoolServerContainerRefOperation;
import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
import com.omgservers.service.server.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.service.server.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DeleteRuntimePoolServerContainerRefMethodImpl implements DeleteRuntimePoolServerContainerRefMethod {

    final DeleteRuntimePoolServerContainerRefOperation deleteRuntimePoolServerContainerRefOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeleteRuntimePoolServerContainerRefResponse> deleteRuntimePoolServerContainerRef(
            final DeleteRuntimePoolServerContainerRefRequest request) {
        log.debug("Delete runtime pool server container ref, request={}", request);

        final var runtimeId = request.getRuntimeId();
        final var id = request.getId();
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) ->
                                        deleteRuntimePoolServerContainerRefOperation
                                                .deleteRuntimePoolServerContainerRef(changeContext,
                                                        sqlConnection,
                                                        shardModel.shard(),
                                                        runtimeId,
                                                        id))
                        .map(ChangeContext::getResult))
                .map(DeleteRuntimePoolServerContainerRefResponse::new);
    }
}
