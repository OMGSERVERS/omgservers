package com.omgservers.service.server.task.impl.method.executePoolTask.component;

import com.omgservers.schema.model.poolServer.PoolServerModel;
import com.omgservers.schema.model.poolSeverContainer.PoolContainerModel;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class SchedulerServer {

    @Getter
    final PoolServerModel poolServer;

    @Getter
    final Long id;
    final List<SchedulerContainer> containers;
    final Integer maxMemoryUsage;
    final Integer maxContainers;
    final Integer maxCpuTime;

    Long cpuTime;
    Long memoryUsage;

    public SchedulerServer(final PoolServerModel poolServer) {
        this.poolServer = poolServer;

        id = poolServer.getId();
        containers = new ArrayList<>();
        final var dockerHostConfig = poolServer.getConfig().getDockerHostConfig();
        maxCpuTime = dockerHostConfig.getCpuCount() * 100;
        maxMemoryUsage = dockerHostConfig.getMemorySize();
        maxContainers = dockerHostConfig.getMaxContainers();

        cpuTime = 0L;
        memoryUsage = 0L;
    }

    public void addPoolContainer(final PoolContainerModel poolContainer) {
        final var container = new SchedulerContainer(poolContainer);
        addContainer(container);
    }

    public boolean schedule(final SchedulerContainer container) {
        if (containers.size() >= maxContainers) {
            return false;
        }

        final var cpuLimit = container.getCpuLimit();
        if (cpuTime + cpuLimit >= maxCpuTime) {
            return false;
        }

        final var memoryLimit = container.getMemoryLimit();
        if (memoryUsage + memoryLimit >= maxMemoryUsage) {
            return false;
        }

        addContainer(container);

        return true;
    }

    void addContainer(final SchedulerContainer container) {
        cpuTime += container.getCpuLimit();
        memoryUsage += container.getMemoryLimit();
        containers.add(container);
    }
}
