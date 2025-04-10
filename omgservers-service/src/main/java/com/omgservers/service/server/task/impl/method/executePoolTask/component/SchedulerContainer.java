package com.omgservers.service.server.task.impl.method.executePoolTask.component;

import com.omgservers.schema.model.poolRequest.PoolRequestModel;
import com.omgservers.schema.model.poolContainer.PoolContainerModel;
import lombok.Getter;

public class SchedulerContainer {

    final PoolContainerModel poolContainer;
    final PoolRequestModel poolRequest;

    @Getter
    final Long cpuLimit;

    @Getter
    final Long memoryLimit;

    public SchedulerContainer(final PoolContainerModel poolContainer) {
        this.poolContainer = poolContainer;
        poolRequest = null;
        final var poolContainerConfig = poolContainer.getConfig();
        cpuLimit = poolContainerConfig.getCpuLimitInMilliseconds();
        memoryLimit = poolContainerConfig.getMemoryLimitInMegabytes();
    }

    public SchedulerContainer(final PoolRequestModel poolRequest) {
        this.poolRequest = poolRequest;
        poolContainer = null;
        final var containerConfig = poolRequest.getConfig().getContainerConfig();
        cpuLimit = containerConfig.getCpuLimitInMilliseconds();
        memoryLimit = containerConfig.getMemoryLimitInMegabytes();
    }
}
