package com.omgservers.service.service.task.impl.method.executePoolTask.operation;

import com.omgservers.schema.model.poolRequest.PoolRequestModel;
import com.omgservers.schema.model.poolServer.PoolServerModel;
import com.omgservers.schema.model.poolSeverContainer.PoolContainerModel;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class PoolScheduler {

    final Map<Long, Server> indexByServerId;

    public PoolScheduler(final List<PoolServerModel> poolServers,
                         final List<PoolContainerModel> poolContainers) {

        indexByServerId = poolServers.stream()
                .map(Server::new)
                .collect(Collectors.toMap(Server::getId, server -> server));

        poolContainers.forEach(poolContainer -> {
            final var server = indexByServerId.get(poolContainer.getServerId());
            if (Objects.nonNull(server)) {
                final var container = new Container(poolContainer);
                server.addContainer(container);
            }
        });
    }

    public Optional<PoolServerModel> schedule(final PoolRequestModel poolRequest) {
        final var container = new Container(poolRequest);
        final var servers = new ArrayList<>(indexByServerId.values());
        // TODO: implement different strategies
        Collections.shuffle(servers);

        final var serverOptional = servers.stream()
                .filter(server -> server.checkLimits(container))
                .findAny();

        if (serverOptional.isPresent()) {
            final var server = serverOptional.get();
            server.addContainer(container);
            return Optional.of(server.getPoolServer());
        } else {
            return Optional.empty();
        }
    }

    static class Server {

        @Getter
        final PoolServerModel poolServer;

        @Getter
        final Long id;
        final List<Container> containers;
        final Integer maxMemoryUsage;
        final Integer maxContainers;
        final Integer maxCpuTime;

        Long cpuTime;
        Long memoryUsage;

        public Server(final PoolServerModel poolServer) {
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

        boolean checkLimits(final Container container) {
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

            return true;
        }

        void addContainer(final Container container) {
            cpuTime += container.getCpuLimit();
            memoryUsage += container.getMemoryLimit();
            containers.add(container);
        }
    }

    @Data
    static class Container {

        final PoolContainerModel poolContainer;
        final PoolRequestModel poolRequest;
        final Long cpuLimit;
        final Long memoryLimit;

        public Container(final PoolContainerModel poolContainer) {
            this.poolContainer = poolContainer;
            poolRequest = null;
            final var poolContainerConfig = poolContainer.getConfig();
            cpuLimit = poolContainerConfig.getCpuLimitInMilliseconds();
            memoryLimit = poolContainerConfig.getMemoryLimitInMegabytes();
        }

        public Container(final PoolRequestModel poolRequest) {
            this.poolRequest = poolRequest;
            poolContainer = null;
            final var containerConfig = poolRequest.getConfig().getContainerConfig();
            cpuLimit = containerConfig.getCpuLimitInMilliseconds();
            memoryLimit = containerConfig.getMemoryLimitInMegabytes();
        }
    }
}
