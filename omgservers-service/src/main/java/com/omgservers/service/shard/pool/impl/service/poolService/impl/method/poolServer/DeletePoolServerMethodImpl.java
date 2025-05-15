package com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolServer;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.pool.poolServer.DeletePoolServerRequest;
import com.omgservers.schema.shard.pool.poolServer.DeletePoolServerResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.pool.impl.operation.poolServer.DeletePoolServerOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DeletePoolServerMethodImpl implements DeletePoolServerMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final DeletePoolServerOperation deletePoolServerOperation;

    @Override
    public Uni<DeletePoolServerResponse> execute(final ShardModel shardModel,
                                                 final DeletePoolServerRequest request) {
        log.debug("{}", request);

        final var poolId = request.getPoolId();
        final var id = request.getId();
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        deletePoolServerOperation.execute(changeContext,
                                sqlConnection,
                                shardModel.slot(),
                                poolId,
                                id))
                .map(ChangeContext::getResult)
                .map(DeletePoolServerResponse::new);
    }
}
