package com.omgservers.service.handler.pool;

import com.omgservers.model.dto.pool.pool.GetPoolRequest;
import com.omgservers.model.dto.pool.pool.GetPoolResponse;
import com.omgservers.model.dto.pool.poolServer.SyncPoolServerRequest;
import com.omgservers.model.dto.pool.poolServer.SyncPoolServerResponse;
import com.omgservers.model.dto.root.GetRootRequest;
import com.omgservers.model.dto.root.GetRootResponse;
import com.omgservers.model.dto.system.SyncEventRequest;
import com.omgservers.model.dto.system.SyncEventResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.job.PoolJobTaskExecutionRequestedEventBodyModel;
import com.omgservers.model.event.body.module.pool.PoolCreatedEventBodyModel;
import com.omgservers.model.pool.PoolModel;
import com.omgservers.model.poolServer.PoolServerConfigModel;
import com.omgservers.model.poolServer.PoolServerModel;
import com.omgservers.model.poolServer.PoolServerQualifierEnum;
import com.omgservers.model.root.RootModel;
import com.omgservers.service.factory.pool.PoolModelFactory;
import com.omgservers.service.factory.pool.PoolServerModelFactory;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.pool.PoolModule;
import com.omgservers.service.module.root.RootModule;
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
    final PoolModule poolModule;
    final RootModule rootModule;

    final GetConfigOperation getConfigOperation;

    final PoolServerModelFactory poolServerModelFactory;
    final EventModelFactory eventModelFactory;
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

                    final var idempotencyKey = event.getIdempotencyKey();

                    final var rootId = pool.getRootId();
                    return getRoot(rootId)
                            .flatMap(root -> {
                                if (root.getDefaultPoolId().equals(poolId)) {
                                    return fillDefaultPool(pool, idempotencyKey);
                                } else {
                                    return Uni.createFrom().voidItem();
                                }
                            })
                            .flatMap(voidItem -> requestJobExecution(poolId, idempotencyKey));
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
        final var localHostConfig = getConfigOperation.getServiceConfig().bootstrap().localHost();
        if (localHostConfig.enabled()) {

            final var serverConfig = PoolServerConfigModel.create();
            serverConfig.setDockerHostConfig(new PoolServerConfigModel.DockerHostConfig(
                    localHostConfig.uri(),
                    localHostConfig.cpuCount(),
                    localHostConfig.memorySize(),
                    localHostConfig.maxContainers()));

            final var poolServer = poolServerModelFactory.create(pool.getId(),
                    PoolServerQualifierEnum.DOCKER_HOST,
                    serverConfig,
                    idempotencyKey);

            return syncPoolServer(poolServer)
                    .replaceWithVoid();
        } else {
            return Uni.createFrom().voidItem();
        }
    }

    Uni<Boolean> syncPoolServer(final PoolServerModel poolServer) {
        final var request = new SyncPoolServerRequest(poolServer);
        return poolModule.getPoolService().syncPoolServerWithIdempotency(request)
                .map(SyncPoolServerResponse::getCreated);
    }

    Uni<Boolean> requestJobExecution(final Long poolId, final String idempotencyKey) {
        final var eventBody = new PoolJobTaskExecutionRequestedEventBodyModel(poolId);
        final var eventModel = eventModelFactory.create(eventBody,
                idempotencyKey + "/" + eventBody.getQualifier());

        final var syncEventRequest = new SyncEventRequest(eventModel);
        return systemModule.getEventService().syncEventWithIdempotency(syncEventRequest)
                .map(SyncEventResponse::getCreated);
    }
}
