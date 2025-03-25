package com.omgservers.service.shard.pool.impl.service.poolService.impl.method.pool;

import com.omgservers.schema.module.pool.pool.DeletePoolRequest;
import com.omgservers.schema.module.pool.pool.DeletePoolResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import com.omgservers.service.shard.pool.impl.operation.pool.DeletePoolOperation;
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
    public Uni<DeletePoolResponse> execute(final DeletePoolRequest request) {
        log.trace("{}", request);

        final var id = request.getId();
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) -> deletePoolOperation.execute(changeContext,
                                        sqlConnection,
                                        shardModel.shard(),
                                        id))
                        .map(ChangeContext::getResult))
                .map(DeletePoolResponse::new);
    }
}
