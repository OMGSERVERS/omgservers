package com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolContainer;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.pool.poolContainer.DeletePoolContainerRequest;
import com.omgservers.schema.shard.pool.poolContainer.DeletePoolContainerResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.pool.impl.operation.poolContainer.DeletePoolContainerOperation;
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

    @Override
    public Uni<DeletePoolContainerResponse> execute(final ShardModel shardModel,
                                                    final DeletePoolContainerRequest request) {
        log.debug("{}", request);

        final var poolId = request.getPoolId();
        final var id = request.getId();
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        deletePoolContainerOperation.execute(changeContext,
                                sqlConnection,
                                shardModel.slot(),
                                poolId,
                                id))
                .map(ChangeContext::getResult)
                .map(DeletePoolContainerResponse::new);
    }
}
