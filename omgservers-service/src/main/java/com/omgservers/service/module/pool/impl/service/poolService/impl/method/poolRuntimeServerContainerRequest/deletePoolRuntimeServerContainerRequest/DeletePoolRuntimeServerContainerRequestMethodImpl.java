package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolRuntimeServerContainerRequest.deletePoolRuntimeServerContainerRequest;

import com.omgservers.model.dto.pool.poolRuntimeServerContainerRequest.DeletePoolRuntimeServerContainerRequestRequest;
import com.omgservers.model.dto.pool.poolRuntimeServerContainerRequest.DeletePoolRuntimeServerContainerRequestResponse;
import com.omgservers.service.module.pool.impl.operation.poolRuntimeServerContainerRequest.deletePoolRuntimeServerContainerRequest.DeletePoolRuntimeServerContainerRequestOperation;
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
class DeletePoolRuntimeServerContainerRequestMethodImpl implements DeletePoolRuntimeServerContainerRequestMethod {

    final DeletePoolRuntimeServerContainerRequestOperation deletePoolRuntimeServerContainerRequestOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeletePoolRuntimeServerContainerRequestResponse> deletePoolRuntimeServerContainerRequest(
            final DeletePoolRuntimeServerContainerRequestRequest request) {
        log.debug("Delete pool runtime server container request, request={}", request);

        final var poolId = request.getPoolId();
        final var id = request.getId();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) -> deletePoolRuntimeServerContainerRequestOperation
                                        .deletePoolRuntimeServerContainerRequest(changeContext,
                                                sqlConnection,
                                                shardModel.shard(),
                                                poolId,
                                                id))
                        .map(ChangeContext::getResult))
                .map(DeletePoolRuntimeServerContainerRequestResponse::new);
    }
}
