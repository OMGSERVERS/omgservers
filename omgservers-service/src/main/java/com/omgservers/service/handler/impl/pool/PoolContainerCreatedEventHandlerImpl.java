package com.omgservers.service.handler.impl.pool;

import com.omgservers.schema.model.poolServer.PoolServerModel;
import com.omgservers.schema.model.poolContainer.PoolContainerModel;
import com.omgservers.schema.module.docker.StartDockerContainerRequest;
import com.omgservers.schema.module.docker.StartDockerContainerResponse;
import com.omgservers.schema.module.pool.poolContainer.GetPoolContainerRequest;
import com.omgservers.schema.module.pool.poolContainer.GetPoolContainerResponse;
import com.omgservers.schema.module.pool.poolServer.GetPoolServerRequest;
import com.omgservers.schema.module.pool.poolServer.GetPoolServerResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.pool.PoolContainerCreatedEventBodyModel;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.shard.docker.DockerShard;
import com.omgservers.service.shard.pool.PoolShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class PoolContainerCreatedEventHandlerImpl implements EventHandler {

    final DockerShard dockerShard;
    final PoolShard poolShard;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.POOL_CONTAINER_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("Handle event, {}", event);

        final var body = (PoolContainerCreatedEventBodyModel) event.getBody();
        final var poolId = body.getPoolId();
        final var id = body.getId();

        return getPoolContainer(poolId, id)
                .flatMap(poolContainer -> {
                    log.debug("Created, {}", poolContainer);

                    final var poolServerId = poolContainer.getServerId();
                    return getPoolServer(poolId, poolServerId)
                            .flatMap(poolServer -> startDockerContainer(poolServer, poolContainer));
                })
                .replaceWithVoid();
    }

    Uni<PoolContainerModel> getPoolContainer(final Long poolId,
                                             final Long id) {
        final var request = new GetPoolContainerRequest(poolId, id);
        return poolShard.getService().execute(request)
                .map(GetPoolContainerResponse::getPoolContainer);
    }

    Uni<PoolServerModel> getPoolServer(final Long poolId, final Long id) {
        final var request = new GetPoolServerRequest(poolId, id);
        return poolShard.getService().execute(request)
                .map(GetPoolServerResponse::getPoolServer);
    }

    Uni<Boolean> startDockerContainer(final PoolServerModel poolServer,
                                      final PoolContainerModel poolContainer) {
        final var request = new StartDockerContainerRequest(poolServer, poolContainer);
        return dockerShard.getService().execute(request)
                .map(StartDockerContainerResponse::getStarted);
    }
}
