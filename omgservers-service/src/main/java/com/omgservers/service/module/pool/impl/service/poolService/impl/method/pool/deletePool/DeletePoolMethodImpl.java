package com.omgservers.service.module.pool.impl.service.poolService.impl.method.pool.deletePool;

import com.omgservers.schema.module.pool.pool.DeletePoolRequest;
import com.omgservers.schema.module.pool.pool.DeletePoolResponse;
import com.omgservers.service.module.pool.impl.operation.pool.deletePool.DeletePoolOperation;
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
class DeletePoolMethodImpl implements DeletePoolMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final DeletePoolOperation deletePoolOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeletePoolResponse> deletePool(final DeletePoolRequest request) {
        log.debug("Requested, {}", request);

        final var id = request.getId();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) -> deletePoolOperation.deletePool(changeContext,
                                        sqlConnection,
                                        shardModel.shard(),
                                        id))
                        .map(ChangeContext::getResult))
                .map(DeletePoolResponse::new);
    }
}
