package com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimePoolContainerRef;

import com.omgservers.schema.module.runtime.poolContainerRef.DeleteRuntimePoolContainerRefRequest;
import com.omgservers.schema.module.runtime.poolContainerRef.DeleteRuntimePoolContainerRefResponse;
import com.omgservers.service.shard.runtime.impl.operation.runtimePoolContainerRef.DeleteRuntimePoolContainerRefOperation;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DeleteRuntimePoolContainerRefMethodImpl implements DeleteRuntimePoolContainerRefMethod {

    final DeleteRuntimePoolContainerRefOperation deleteRuntimePoolContainerRefOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeleteRuntimePoolContainerRefResponse> execute(
            final DeleteRuntimePoolContainerRefRequest request) {
        log.trace("{}", request);

        final var runtimeId = request.getRuntimeId();
        final var id = request.getId();
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) ->
                                        deleteRuntimePoolContainerRefOperation
                                                .execute(changeContext,
                                                        sqlConnection,
                                                        shardModel.shard(),
                                                        runtimeId,
                                                        id))
                        .map(ChangeContext::getResult))
                .map(DeleteRuntimePoolContainerRefResponse::new);
    }
}
