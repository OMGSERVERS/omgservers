package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolRuntimeServerContainerRef.deletePoolRuntimeServerContainerRef;

import com.omgservers.model.dto.pool.poolRuntimeServerContainerRef.DeletePoolRuntimeServerContainerRefRequest;
import com.omgservers.model.dto.pool.poolRuntimeServerContainerRef.DeletePoolRuntimeServerContainerRefResponse;
import com.omgservers.service.module.pool.impl.operation.poolRuntimeServerContainerRef.deletePoolRuntimeServerContainerRef.DeletePoolRuntimeServerContainerRefOperation;
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
class DeletePoolRuntimeServerContainerRefMethodImpl implements DeletePoolRuntimeServerContainerRefMethod {

    final DeletePoolRuntimeServerContainerRefOperation deletePoolRuntimeServerContainerRefOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeletePoolRuntimeServerContainerRefResponse> deletePoolRuntimeServerContainerRef(
            final DeletePoolRuntimeServerContainerRefRequest request) {
        log.debug("Delete pool runtime server container ref, request={}", request);

        final var poolId = request.getPoolId();
        final var id = request.getId();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) -> deletePoolRuntimeServerContainerRefOperation
                                        .deletePoolRuntimeServerContainerRef(changeContext,
                                                sqlConnection,
                                                shardModel.shard(),
                                                poolId,
                                                id))
                        .map(ChangeContext::getResult))
                .map(DeletePoolRuntimeServerContainerRefResponse::new);
    }
}
