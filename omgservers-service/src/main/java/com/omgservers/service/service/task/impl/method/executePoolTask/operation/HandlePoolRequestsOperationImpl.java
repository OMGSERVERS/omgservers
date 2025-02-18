package com.omgservers.service.service.task.impl.method.executePoolTask.operation;

import com.omgservers.schema.model.poolChangeOfState.PoolChangeOfStateDto;
import com.omgservers.schema.model.poolRequest.PoolRequestModel;
import com.omgservers.schema.model.poolServer.PoolServerModel;
import com.omgservers.schema.model.poolSeverContainer.PoolContainerConfigDto;
import com.omgservers.schema.model.poolSeverContainer.PoolContainerModel;
import com.omgservers.schema.model.poolState.PoolStateDto;
import com.omgservers.service.factory.pool.PoolContainerModelFactory;
import com.omgservers.service.shard.pool.PoolShard;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HandlePoolRequestsOperationImpl implements HandlePoolRequestsOperation {

    final PoolShard poolShard;

    final PoolContainerModelFactory poolContainerModelFactory;

    @Override
    public void execute(final PoolStateDto poolState,
                        final PoolChangeOfStateDto poolChangeOfState) {
        final var poolScheduler = new PoolScheduler(poolState.getServers(), poolState.getContainers());
        poolState.getRequests().forEach(poolRequest -> handlePoolRequest(poolChangeOfState,
                poolScheduler,
                poolRequest));
    }

    void handlePoolRequest(final PoolChangeOfStateDto poolChangeOfState,
                           final PoolScheduler poolScheduler,
                           final PoolRequestModel poolRequest) {
        final var poolServerOptional = poolScheduler.schedule(poolRequest);
        if (poolServerOptional.isPresent()) {
            final var poolServer = poolServerOptional.get();
            final var poolContainer = createPoolContainer(poolServer, poolRequest);
            poolChangeOfState.getContainersToSync().add(poolContainer);
            poolChangeOfState.getRequestsToDelete().add(poolRequest);
        }
    }

    PoolContainerModel createPoolContainer(final PoolServerModel poolServer,
                                           final PoolRequestModel poolRequest) {
        final var poolId = poolServer.getPoolId();
        final var poolServerId = poolServer.getId();
        final var runtimeId = poolRequest.getRuntimeId();
        final var runtimeQualifier = poolRequest.getRuntimeQualifier();
        final var config = PoolContainerConfigDto.create();
        config.setImageId(poolRequest.getConfig().getContainerConfig().getImageId());
        config.setLabels(poolRequest.getConfig().getContainerConfig().getLabels());
        config.setCpuLimitInMilliseconds(poolRequest.getConfig()
                .getContainerConfig().getCpuLimitInMilliseconds());
        config.setMemoryLimitInMegabytes(poolRequest.getConfig()
                .getContainerConfig().getMemoryLimitInMegabytes());
        config.setEnvironment(poolRequest.getConfig().getContainerConfig().getEnvironment());
        final var idempotencyKey = poolRequest.getId().toString();
        final var poolContainer = poolContainerModelFactory.create(poolId,
                poolServerId,
                runtimeId,
                runtimeQualifier,
                config,
                idempotencyKey);
        return poolContainer;
    }
}
