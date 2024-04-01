package com.omgservers.service.handler.pool;

import com.omgservers.model.dto.pool.pool.GetPoolRequest;
import com.omgservers.model.dto.pool.pool.GetPoolResponse;
import com.omgservers.model.dto.root.GetRootRequest;
import com.omgservers.model.dto.root.GetRootResponse;
import com.omgservers.model.dto.server.SyncServerRequest;
import com.omgservers.model.dto.server.SyncServerResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.module.pool.PoolCreatedEventBodyModel;
import com.omgservers.model.pool.PoolModel;
import com.omgservers.model.root.RootModel;
import com.omgservers.model.server.ServerConfigModel;
import com.omgservers.model.server.ServerModel;
import com.omgservers.model.server.ServerQualifierEnum;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.pool.PoolModelFactory;
import com.omgservers.service.factory.server.ServerModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.pool.PoolModule;
import com.omgservers.service.module.root.RootModule;
import com.omgservers.service.module.server.ServerModule;
import com.omgservers.service.module.system.SystemModule;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class PoolCreatedEventHandlerImpl implements EventHandler {

    final SystemModule systemModule;
    final ServerModule serverModule;
    final PoolModule poolModule;
    final RootModule rootModule;

    final GetConfigOperation getConfigOperation;

    final ServerModelFactory serverModelFactory;
    final PoolModelFactory poolModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.POOL_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (PoolCreatedEventBodyModel) event.getBody();
        final var poolId = body.getId();

        return getPool(poolId)
                .flatMap(pool -> {
                    log.info("Pool was created, pool={}, root={}", poolId, pool.getRootId());

                    final var rootId = pool.getRootId();
                    return getRoot(rootId)
                            .flatMap(root -> {
                                final var idempotencyKey = event.getIdempotencyKey();

                                if (root.getDefaultPoolId().equals(poolId)) {
                                    return fillDefaultPool(pool, idempotencyKey);
                                } else {
                                    return Uni.createFrom().voidItem();
                                }
                            });
                })
                .replaceWithVoid();
    }

    Uni<PoolModel> getPool(final Long id) {
        final var request = new GetPoolRequest(id);
        return poolModule.getPoolService().getPool(request)
                .map(GetPoolResponse::getPool);
    }

    Uni<RootModel> getRoot(final Long id) {
        final var request = new GetRootRequest(id);
        return rootModule.getRootService().getRoot(request)
                .map(GetRootResponse::getRoot);
    }

    Uni<Void> fillDefaultPool(final PoolModel pool, final String idempotencyKey) {
        final var dockerHostConfig = getConfigOperation.getServiceConfig().bootstrap().dockerHost();
        if (dockerHostConfig.enabled()) {

            final var serverConfig = ServerConfigModel.create();
            serverConfig.setDockerHostConfig(new ServerConfigModel.DockerHostConfig(dockerHostConfig.maxContainers()));

            final var server = serverModelFactory.create(pool.getId(),
                    ServerQualifierEnum.DOCKER_HOST,
                    dockerHostConfig.uri(),
                    dockerHostConfig.cpuCount(),
                    dockerHostConfig.memorySize(),
                    serverConfig,
                    idempotencyKey);

            return syncServer(server)
                    .replaceWithVoid();
        } else {
            return Uni.createFrom().voidItem();
        }
    }

    Uni<Boolean> syncServer(final ServerModel server) {
        final var request = new SyncServerRequest(server);
        return serverModule.getServerService().syncServer(request)
                .map(SyncServerResponse::getCreated)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATION)) {
                            log.warn("Idempotency was violated, object={}, {}", server, t.getMessage());
                            return Uni.createFrom().item(Boolean.FALSE);
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }
}
