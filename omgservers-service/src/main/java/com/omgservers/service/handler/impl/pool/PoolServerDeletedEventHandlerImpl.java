package com.omgservers.service.handler.impl.pool;

import com.omgservers.schema.model.poolServer.PoolServerModel;
import com.omgservers.schema.model.poolSeverContainer.PoolContainerModel;
import com.omgservers.schema.module.pool.poolContainer.ViewPoolContainersRequest;
import com.omgservers.schema.module.pool.poolContainer.ViewPoolContainersResponse;
import com.omgservers.schema.module.pool.poolServer.GetPoolServerRequest;
import com.omgservers.schema.module.pool.poolServer.GetPoolServerResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.pool.PoolServerDeletedEventBodyModel;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.shard.pool.PoolShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class PoolServerDeletedEventHandlerImpl implements EventHandler {

    final PoolShard poolShard;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.POOL_SERVER_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("Handle event, {}", event);

        final var body = (PoolServerDeletedEventBodyModel) event.getBody();
        final var poolId = body.getPoolId();
        final var poolServerId = body.getId();

        return getPoolServer(poolId, poolServerId)
                .flatMap(poolServer -> {
                    log.debug("Deleted, {}", poolServer);

                    return viewPoolContainers(poolId, poolServerId)
                            .invoke(poolContainers -> {
                                if (poolContainers.isEmpty()) {
                                    log.info("Server \"{}:{}\" deleted, no containers were found",
                                            poolId, poolServerId);
                                } else {
                                    log.error("Server \"{}:{}\" deleted, but there are still {} containers",
                                            poolId, poolServerId, poolContainers.size());
                                }
                            });
                })
                .replaceWithVoid();
    }

    Uni<PoolServerModel> getPoolServer(final Long poolId, final Long id) {
        final var request = new GetPoolServerRequest(poolId, id);
        return poolShard.getService().execute(request)
                .map(GetPoolServerResponse::getPoolServer);
    }

    Uni<List<PoolContainerModel>> viewPoolContainers(final Long poolId,
                                                     final Long serverId) {
        final var request = new ViewPoolContainersRequest(poolId, serverId);
        return poolShard.getService().execute(request)
                .map(ViewPoolContainersResponse::getPoolContainers);
    }
}
