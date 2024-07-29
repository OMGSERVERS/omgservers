package com.omgservers.service.handler.pool;

import com.omgservers.schema.event.body.module.pool.PoolServerContainerCreatedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.pool.testInterface.PoolServerContainerCreatedEventHandlerImplTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class PoolServerContainerCreatedEventHandlerImplTest extends Assertions {

    @Inject
    PoolServerContainerCreatedEventHandlerImplTestInterface poolServerContainerCreatedEventHandler;

    @Inject
    EventModelFactory eventModelFactory;

    @Inject
    TestDataFactory testDataFactory;

    @Test
    @Disabled
    void givenHandler_whenRetry_thenFinished() {
        final var defaultPool = testDataFactory.getPoolTestDataFactory().createDefaultPool();
        final var defaultPoolServer = testDataFactory.getPoolTestDataFactory().createPoolServer(defaultPool);
        final var tenant = testDataFactory.getTenantTestDataFactory().createTenant();
        final var project = testDataFactory.getTenantTestDataFactory().createProject(tenant);
        final var stage = testDataFactory.getTenantTestDataFactory().createStage(project);
        final var version = testDataFactory.getTenantTestDataFactory().createVersion(stage);
        final var lobby = testDataFactory.getLobbyTestDataFactory().createLobby(version);
        final var lobbyRuntime = testDataFactory.getRuntimeTestDataFactory()
                .createLobbyRuntime(tenant, version, lobby);
        final var poolServerContainer = testDataFactory.getPoolTestDataFactory()
                .createPoolServerContainer(defaultPoolServer, lobbyRuntime);

        final var poolId = poolServerContainer.getPoolId();
        final var serverId = poolServerContainer.getServerId();
        final var id = poolServerContainer.getId();

        final var eventBody = new PoolServerContainerCreatedEventBodyModel(poolId, serverId, id);
        final var eventModel = eventModelFactory.create(eventBody);

        poolServerContainerCreatedEventHandler.handle(eventModel);
        log.info("Retry");
        poolServerContainerCreatedEventHandler.handle(eventModel);
    }
}