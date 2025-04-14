package com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolRequest;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.pool.poolRequest.DeletePoolRequestRequest;
import com.omgservers.schema.shard.pool.poolRequest.DeletePoolRequestResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.pool.impl.operation.poolRequest.DeletePoolRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DeletePoolRequestMethodImpl implements DeletePoolRequestMethod {

    final DeletePoolRequestOperation deletePoolRequestOperation;
    final ChangeWithContextOperation changeWithContextOperation;

    @Override
    public Uni<DeletePoolRequestResponse> execute(final ShardModel shardModel,
                                                  final DeletePoolRequestRequest request) {
        log.trace("{}", request);

        final var poolId = request.getPoolId();
        final var id = request.getId();
        return changeWithContextOperation.<Boolean>changeWithContext(
                        (changeContext, sqlConnection) -> deletePoolRequestOperation
                                .execute(changeContext,
                                        sqlConnection,
                                        shardModel.shard(),
                                        poolId,
                                        id))
                .map(ChangeContext::getResult)
                .map(DeletePoolRequestResponse::new);
    }
}
