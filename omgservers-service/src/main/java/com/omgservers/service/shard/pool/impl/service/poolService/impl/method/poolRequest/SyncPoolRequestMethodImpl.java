package com.omgservers.service.shard.pool.impl.service.poolService.impl.method.poolRequest;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.pool.poolRequest.SyncPoolRequestRequest;
import com.omgservers.schema.shard.pool.poolRequest.SyncPoolRequestResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.pool.impl.operation.pool.VerifyPoolExistsOperation;
import com.omgservers.service.shard.pool.impl.operation.poolRequest.UpsertPoolRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncPoolRequestMethodImpl implements SyncPoolRequestMethod {

    final UpsertPoolRequestOperation upsertPoolRequestOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final VerifyPoolExistsOperation verifyPoolExistsOperation;

    @Override
    public Uni<SyncPoolRequestResponse> execute(final ShardModel shardModel,
                                                final SyncPoolRequestRequest request) {
        log.trace("{}", request);

        final var poolRequest = request.getPoolRequest();
        final var poolId = poolRequest.getPoolId();

        final var slot = shardModel.slot();
        return changeWithContextOperation.<Boolean>changeWithContext((context, sqlConnection) ->
                        verifyPoolExistsOperation.execute(sqlConnection, slot, poolId)
                                .flatMap(exists -> {
                                    if (exists) {
                                        return upsertPoolRequestOperation
                                                .execute(
                                                        context,
                                                        sqlConnection,
                                                        slot,
                                                        poolRequest);
                                    } else {
                                        throw new ServerSideNotFoundException(
                                                ExceptionQualifierEnum.PARENT_NOT_FOUND,
                                                "pool does not exist or was deleted, id=" + poolId);
                                    }
                                }))
                .map(ChangeContext::getResult)
                .map(SyncPoolRequestResponse::new);
    }
}
