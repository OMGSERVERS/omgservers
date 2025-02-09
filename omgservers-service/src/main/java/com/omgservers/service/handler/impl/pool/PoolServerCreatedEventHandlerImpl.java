package com.omgservers.service.handler.impl.pool;

import com.omgservers.schema.model.poolServer.PoolServerModel;
import com.omgservers.schema.module.docker.PingDockerHostRequest;
import com.omgservers.schema.module.docker.PingDockerHostResponse;
import com.omgservers.schema.module.pool.poolServer.GetPoolServerRequest;
import com.omgservers.schema.module.pool.poolServer.GetPoolServerResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.pool.PoolServerCreatedEventBodyModel;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.shard.docker.DockerShard;
import com.omgservers.service.shard.pool.PoolShard;
import com.omgservers.service.shard.runtime.RuntimeShard;
import com.omgservers.service.operation.server.GetServiceConfigOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class PoolServerCreatedEventHandlerImpl implements EventHandler {

    final RuntimeShard runtimeShard;
    final DockerShard dockerShard;
    final PoolShard poolShard;

    final GetServiceConfigOperation getServiceConfigOperation;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.POOL_SERVER_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("Handle event, {}", event);

        final var body = (PoolServerCreatedEventBodyModel) event.getBody();
        final var poolId = body.getPoolId();
        final var poolServerId = body.getId();

        return getPoolServer(poolId, poolServerId)
                .flatMap(poolServer -> {
                    log.debug("Created, {}", poolServer);

                    final var pingDockerHostRequest = new PingDockerHostRequest(poolServer);
                    return dockerShard.getService().execute(pingDockerHostRequest)
                            .map(PingDockerHostResponse::getSuccessful)
                            .invoke(successful -> {
                                if (successful) {
                                    log.debug("Pool server \"{}\" was created and successfully pinged", poolServerId);
                                } else {
                                    log.error("Pool server \"{}\" was created, but couldn't be reached", poolServerId);
                                }
                            });
                })
                .replaceWithVoid();
    }

    Uni<PoolServerModel> getPoolServer(final Long poolId, final Long id) {
        final var request = new GetPoolServerRequest(poolId, id);
        return poolShard.getPoolService().execute(request)
                .map(GetPoolServerResponse::getPoolServer);
    }
}
