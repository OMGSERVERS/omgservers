package com.omgservers.service.service.task.impl.method.executePoolTask.operation;

import com.omgservers.schema.model.poolChangeOfState.PoolChangeOfStateDto;
import com.omgservers.schema.module.pool.poolState.UpdatePoolStateRequest;
import com.omgservers.schema.module.pool.poolState.UpdatePoolStateResponse;
import com.omgservers.service.service.task.impl.method.executePoolTask.dto.HandlePoolResult;
import com.omgservers.service.shard.pool.PoolShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class UpdatePoolOperationImpl implements UpdatePoolOperation {

    final PoolShard poolShard;

    @Override
    public Uni<Void> execute(final HandlePoolResult handlePoolResult) {
        final var poolId = handlePoolResult.poolId();
        final var poolChangeOfState = handlePoolResult.poolChangeOfState();

        return updatePoolState(poolId, poolChangeOfState)
                .replaceWithVoid();
    }

    Uni<Boolean> updatePoolState(final Long poolId,
                                 final PoolChangeOfStateDto poolChangeOfState) {
        final var request = new UpdatePoolStateRequest(poolId, poolChangeOfState);
        return poolShard.getService().execute(request)
                .map(UpdatePoolStateResponse::getUpdated);
    }
}
