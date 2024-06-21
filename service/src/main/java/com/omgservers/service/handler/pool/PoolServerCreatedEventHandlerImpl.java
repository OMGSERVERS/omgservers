package com.omgservers.service.handler.pool;

import com.omgservers.model.dto.pool.poolServer.GetPoolServerRequest;
import com.omgservers.model.dto.pool.poolServer.GetPoolServerResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.module.pool.PoolServerCreatedEventBodyModel;
import com.omgservers.model.poolServer.PoolServerModel;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.pool.PoolModule;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import com.omgservers.service.operation.getDockerClient.GetDockerClientOperation;
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
    final PoolModule poolModule;

    final GetDockerClientOperation getDockerClientOperation;
    final GetConfigOperation getConfigOperation;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.POOL_SERVER_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (PoolServerCreatedEventBodyModel) event.getBody();
        final var poolId = body.getPoolId();
        final var id = body.getId();

        return getPoolServer(poolId, id)
                .flatMap(server -> {

                    final var dockerDaemonUri = server.getConfig().getDockerHostConfig().getDockerDaemonUri();
                    final var dockerClient = getDockerClientOperation.getClient(dockerDaemonUri);

                    try {
                        dockerClient.pingCmd().exec();
                        log.info("Pool server was created and checked, id={}/{}, dockerDaemonUri={}",
                                poolId, id, dockerDaemonUri);
                    } catch (Exception e) {
                        log.error("Pool server was created but couldn't be reached, " +
                                        "id={}/{}, dockerDaemonUri={}, {}:{}",
                                poolId, id, dockerDaemonUri, e.getClass().getSimpleName(), e.getMessage());
                    }

                    return Uni.createFrom().voidItem();
                })
                .replaceWithVoid();
    }

    Uni<PoolServerModel> getPoolServer(final Long poolId, final Long id) {
        final var request = new GetPoolServerRequest(poolId, id);
        return poolModule.getPoolService().getPoolServer(request)
                .map(GetPoolServerResponse::getPoolServer);
    }
}
