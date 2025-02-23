package com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolContainer;

import com.omgservers.schema.module.pool.poolContainer.DeletePoolContainerRequest;
import com.omgservers.schema.module.pool.poolContainer.DeletePoolContainerResponse;
import com.omgservers.service.shard.pool.impl.operation.poolContainer.DeletePoolContainerOperation;
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
class DeletePoolContainerMethodImpl implements DeletePoolContainerMethod {

    final DeletePoolContainerOperation deletePoolContainerOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeletePoolContainerResponse> execute(
            final DeletePoolContainerRequest request) {
        log.trace("{}", request);

        final var poolId = request.getPoolId();
        final var serverId = request.getServerId();
        final var id = request.getId();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) -> deletePoolContainerOperation
                                        .execute(changeContext,
                                                sqlConnection,
                                                shardModel.shard(),
                                                poolId,
                                                serverId,
                                                id))
                        .map(ChangeContext::getResult))
                .map(DeletePoolContainerResponse::new);
    }
}
