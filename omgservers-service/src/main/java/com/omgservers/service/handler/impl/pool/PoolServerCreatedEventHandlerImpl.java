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
import com.omgservers.service.module.docker.DockerModule;
import com.omgservers.service.module.pool.PoolModule;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class PoolServerCreatedEventHandlerImpl implements EventHandler {

    final RuntimeModule runtimeModule;
    final DockerModule dockerModule;
    final PoolModule poolModule;

    final GetConfigOperation getConfigOperation;

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
                    return dockerModule.getService().execute(pingDockerHostRequest)
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
        return poolModule.getPoolService().execute(request)
                .map(GetPoolServerResponse::getPoolServer);
    }
}
