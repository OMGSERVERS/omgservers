package com.omgservers.service.factory;

import com.omgservers.model.serverContainer.ServerContainerConfigModel;
import com.omgservers.model.serverContainer.ServerContainerModel;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ServerContainerModelFactory {

    final GenerateIdOperation generateIdOperation;

    public ServerContainerModel create(final Long serverId,
                                       final String image,
                                       final Integer cpuLimit,
                                       final Integer memoryLimit,
                                       final ServerContainerConfigModel config) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, serverId, image, cpuLimit, memoryLimit, config, idempotencyKey);
    }

    public ServerContainerModel create(final Long serverId,
                                       final String image,
                                       final Integer cpuLimit,
                                       final Integer memoryLimit,
                                       final ServerContainerConfigModel config,
                                       final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, serverId, image, cpuLimit, memoryLimit, config, idempotencyKey);
    }

    public ServerContainerModel create(final Long id,
                                       final Long serverId,
                                       final String image,
                                       final Integer cpuLimit,
                                       final Integer memoryLimit,
                                       final ServerContainerConfigModel config,
                                       final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var serverContainer = new ServerContainerModel();
        serverContainer.setId(id);
        serverContainer.setIdempotencyKey(idempotencyKey);
        serverContainer.setServerId(serverId);
        serverContainer.setCreated(now);
        serverContainer.setModified(now);
        serverContainer.setImage(image);
        serverContainer.setCpuLimit(cpuLimit);
        serverContainer.setMemoryLimit(memoryLimit);
        serverContainer.setConfig(config);
        serverContainer.setDeleted(false);

        return serverContainer;
    }
}
