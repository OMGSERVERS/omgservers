package com.omgservers.service.service.task.impl.method.executePoolTask;

import com.omgservers.schema.model.poolChangeOfState.PoolChangeOfStateDto;
import com.omgservers.schema.model.poolState.PoolStateDto;
import com.omgservers.schema.module.pool.poolState.GetPoolStateRequest;
import com.omgservers.schema.module.pool.poolState.GetPoolStateResponse;
import com.omgservers.schema.module.pool.poolState.UpdatePoolStateRequest;
import com.omgservers.schema.module.pool.poolState.UpdatePoolStateResponse;
import com.omgservers.service.shard.pool.PoolShard;
import com.omgservers.service.service.task.impl.method.executePoolTask.operation.HandlePoolRequestsOperation;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class PoolTaskImpl {

    final PoolShard poolShard;

    final HandlePoolRequestsOperation handlePoolRequestsOperation;

    public Uni<Boolean> execute(final Long poolId) {
        // TODO: using cashing approach
        return getPoolState(poolId)
                .emitOn(Infrastructure.getDefaultWorkerPool())
                .flatMap(poolState -> {
                    final var poolChangeOfState = new PoolChangeOfStateDto();
                    handlePoolRequestsOperation.execute(poolState, poolChangeOfState);
                    return updatePoolState(poolId, poolChangeOfState)
                            .invoke(updated -> {
                                if (poolChangeOfState.isNotEmpty()) {
                                    log.debug("Pool task was executed, poolId={}, {}", poolId,
                                            poolChangeOfState);
                                }

                                final var containersToSync = poolChangeOfState
                                        .getContainersToSync();
                                if (!containersToSync.isEmpty()) {
                                    log.info("The \"{}\" container(s) were scheduled to run in pool {}",
                                            containersToSync.size(), poolId);
                                }
                            });
                })
                .replaceWith(Boolean.TRUE);
    }

    Uni<PoolStateDto> getPoolState(final Long poolId) {
        final var request = new GetPoolStateRequest(poolId);
        return poolShard.getPoolService().execute(request)
                .map(GetPoolStateResponse::getPoolState);
    }

    Uni<Boolean> updatePoolState(final Long poolId,
                                 final PoolChangeOfStateDto poolChangeOfState) {
        final var request = new UpdatePoolStateRequest(poolId, poolChangeOfState);
        return poolShard.getPoolService().execute(request)
                .map(UpdatePoolStateResponse::getUpdated);
    }
}
