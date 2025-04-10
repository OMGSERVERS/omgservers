package com.omgservers.service.server.task.impl.method.executePoolTask.component;

import com.omgservers.schema.model.poolRequest.PoolRequestModel;
import com.omgservers.schema.model.poolServer.PoolServerModel;
import com.omgservers.schema.model.poolContainer.PoolContainerModel;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class PoolScheduler {

    final Map<Long, SchedulerServer> serverById;

    public PoolScheduler() {
        serverById = new HashMap<>();
    }

    public void addPoolServer(final PoolServerModel poolServer) {
        final var server = new SchedulerServer(poolServer);
        serverById.put(server.getId(), server);
    }

    public void addPoolContainer(final PoolContainerModel poolContainer) {
        final var serverId = poolContainer.getServerId();
        final var server = serverById.get(serverId);
        if (Objects.nonNull(server)) {
            server.addPoolContainer(poolContainer);
        } else {
            log.error("Failed to add container, server \"{}\" not found", serverId);
        }
    }

    public Optional<PoolServerModel> schedule(final PoolRequestModel poolRequest) {
        final var container = new SchedulerContainer(poolRequest);
        final var servers = new ArrayList<>(serverById.values());

        // TODO: support different strategies
        Collections.shuffle(servers);

        final var serverOptional = servers.stream()
                .filter(server -> server.schedule(container))
                .findFirst();

        if (serverOptional.isPresent()) {
            final var server = serverOptional.get();
            return Optional.of(server.getPoolServer());
        } else {
            return Optional.empty();
        }
    }
}
