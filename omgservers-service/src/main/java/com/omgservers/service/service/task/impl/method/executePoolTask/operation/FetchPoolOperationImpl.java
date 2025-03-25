package com.omgservers.service.service.task.impl.method.executePoolTask.operation;

import com.omgservers.schema.model.poolState.PoolStateDto;
import com.omgservers.schema.module.pool.poolState.GetPoolStateRequest;
import com.omgservers.schema.module.pool.poolState.GetPoolStateResponse;
import com.omgservers.service.service.task.impl.method.executePoolTask.dto.FetchPoolResult;
import com.omgservers.service.shard.pool.PoolShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class FetchPoolOperationImpl implements FetchPoolOperation {

    final PoolShard poolShard;

    @Override
    public Uni<FetchPoolResult> execute(final Long poolId) {
        return getPoolState(poolId)
                .map(poolState -> new FetchPoolResult(poolId, poolState));
    }

    Uni<PoolStateDto> getPoolState(final Long poolId) {
        final var request = new GetPoolStateRequest(poolId);
        return poolShard.getService().execute(request)
                .map(GetPoolStateResponse::getPoolState);
    }
}
