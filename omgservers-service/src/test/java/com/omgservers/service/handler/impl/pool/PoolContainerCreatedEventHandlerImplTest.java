package com.omgservers.service.handler.impl.pool;

import com.omgservers.BaseTestClass;
import com.omgservers.service.event.body.module.pool.PoolContainerCreatedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.impl.pool.testInterface.PoolContainerCreatedEventHandlerImplTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class PoolContainerCreatedEventHandlerImplTest extends BaseTestClass {

    @Inject
    PoolContainerCreatedEventHandlerImplTestInterface poolContainerCreatedEventHandler;

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
        final var project = testDataFactory.getTenantTestDataFactory().createTenantProject(tenant);
        final var stage = testDataFactory.getTenantTestDataFactory().createStage(project);
        final var version = testDataFactory.getTenantTestDataFactory().createTenantVersion(project);
        final var tenantDeployment = testDataFactory.getTenantTestDataFactory().createTenantDeployment(stage, version);
        final var lobby = testDataFactory.getLobbyTestDataFactory().createLobby(tenantDeployment);
        final var lobbyRuntime = testDataFactory.getRuntimeTestDataFactory()
                .createLobbyRuntime(tenant, tenantDeployment, lobby);
        final var poolContainer = testDataFactory.getPoolTestDataFactory()
                .createPoolContainer(defaultPoolServer, lobbyRuntime);

        final var poolId = poolContainer.getPoolId();
        final var serverId = poolContainer.getServerId();
        final var id = poolContainer.getId();

        final var eventBody = new PoolContainerCreatedEventBodyModel(poolId, serverId, id);
        final var eventModel = eventModelFactory.create(eventBody);

        poolContainerCreatedEventHandler.handle(eventModel);
        log.info("Retry");
        poolContainerCreatedEventHandler.handle(eventModel);
    }
}