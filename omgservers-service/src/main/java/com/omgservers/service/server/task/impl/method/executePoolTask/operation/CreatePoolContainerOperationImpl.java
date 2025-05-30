package com.omgservers.service.server.task.impl.method.executePoolTask.operation;

import com.omgservers.schema.model.poolRequest.PoolRequestModel;
import com.omgservers.schema.model.poolServer.PoolServerModel;
import com.omgservers.schema.model.poolContainer.PoolContainerConfigDto;
import com.omgservers.schema.model.poolContainer.PoolContainerModel;
import com.omgservers.service.factory.pool.PoolContainerModelFactory;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class CreatePoolContainerOperationImpl implements CreatePoolContainerOperation {

    final PoolContainerModelFactory poolContainerModelFactory;

    @Override
    public PoolContainerModel execute(final PoolServerModel poolServer,
                                      final PoolRequestModel poolRequest) {
        final var poolId = poolServer.getPoolId();
        final var poolServerId = poolServer.getId();
        final var runtimeId = poolRequest.getRuntimeId();
        final var runtimeQualifier = poolRequest.getRuntimeQualifier();

        final var config = PoolContainerConfigDto.create();
        config.setImageId(poolRequest.getConfig().getContainerConfig().getImage());
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
