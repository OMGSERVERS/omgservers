package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolServer.deletePoolServer;

import com.omgservers.schema.module.pool.poolServer.DeletePoolServerRequest;
import com.omgservers.schema.module.pool.poolServer.DeletePoolServerResponse;
import com.omgservers.service.module.pool.impl.operation.poolServer.deletePoolServer.DeletePoolServerOperation;
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
class DeletePoolServerMethodImpl implements DeletePoolServerMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final DeletePoolServerOperation deletePoolServerOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeletePoolServerResponse> deletePoolServer(final DeletePoolServerRequest request) {
        log.debug("Requested, {}", request);

        final var poolId = request.getPoolId();
        final var id = request.getId();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) -> deletePoolServerOperation
                                        .deletePoolServer(changeContext,
                                                sqlConnection,
                                                shardModel.shard(),
                                                poolId,
                                                id))
                        .map(ChangeContext::getResult))
                .map(DeletePoolServerResponse::new);
    }
}
