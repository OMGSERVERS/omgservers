package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimeServerContainerRef.deleteRuntimeServerContainerRef;

import com.omgservers.model.dto.runtime.serverRuntimeRef.DeleteRuntimeServerContainerRefRequest;
import com.omgservers.model.dto.runtime.serverRuntimeRef.DeleteRuntimeServerContainerRefResponse;
import com.omgservers.service.module.runtime.impl.operation.runtimeServerContainerRef.deleteRuntimeServerContainerRef.DeleteRuntimeServerContainerRefOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DeleteRuntimeServerContainerRefMethodImpl implements DeleteRuntimeServerContainerRefMethod {

    final DeleteRuntimeServerContainerRefOperation deleteRuntimeServerContainerRefOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeleteRuntimeServerContainerRefResponse> deleteRuntimeServerContainerRef(
            final DeleteRuntimeServerContainerRefRequest request) {
        log.debug("Delete runtime server container ref, request={}", request);

        final var runtimeId = request.getRuntimeId();
        final var id = request.getId();
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) ->
                                        deleteRuntimeServerContainerRefOperation
                                                .deleteRuntimeServerContainerRef(changeContext,
                                                        sqlConnection,
                                                        shardModel.shard(),
                                                        runtimeId,
                                                        id))
                        .map(ChangeContext::getResult))
                .map(DeleteRuntimeServerContainerRefResponse::new);
    }
}
