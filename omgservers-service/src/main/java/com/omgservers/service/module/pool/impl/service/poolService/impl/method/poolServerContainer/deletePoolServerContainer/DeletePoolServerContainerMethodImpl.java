package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolServerContainer.deletePoolServerContainer;

import com.omgservers.model.dto.pool.poolServerContainer.DeletePoolServerContainerRequest;
import com.omgservers.model.dto.pool.poolServerContainer.DeletePoolServerContainerResponse;
import com.omgservers.service.module.pool.impl.operation.poolServerContainer.deletePoolServerContainer.DeletePoolServerContainerOperation;
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
class DeletePoolServerContainerMethodImpl implements DeletePoolServerContainerMethod {

    final DeletePoolServerContainerOperation deletePoolServerContainerOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeletePoolServerContainerResponse> deletePoolServerContainer(
            final DeletePoolServerContainerRequest request) {
        log.debug("Delete pool server container, request={}", request);

        final var poolId = request.getPoolId();
        final var serverId = request.getServerId();
        final var id = request.getId();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) -> deletePoolServerContainerOperation
                                        .deletePoolServerContainer(changeContext,
                                                sqlConnection,
                                                shardModel.shard(),
                                                poolId,
                                                serverId,
                                                id))
                        .map(ChangeContext::getResult))
                .map(DeletePoolServerContainerResponse::new);
    }
}
