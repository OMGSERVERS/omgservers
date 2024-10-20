package com.omgservers.service.handler.impl.pool;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.module.pool.poolServerContainer.DeletePoolServerContainerRequest;
import com.omgservers.service.event.body.module.pool.PoolServerContainerCreatedEventBodyModel;
import com.omgservers.service.event.body.module.pool.PoolServerContainerDeletedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.impl.pool.testInterface.PoolServerContainerCreatedEventHandlerImplTestInterface;
import com.omgservers.service.handler.impl.pool.testInterface.PoolServerContainerDeletedEventHandlerImplTestInterface;
import com.omgservers.service.module.pool.impl.service.poolService.testInterface.PoolServiceTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class PoolServerContainerDeletedEventHandlerImplTest extends BaseTestClass {

    @Inject
    PoolServerContainerDeletedEventHandlerImplTestInterface poolServerContainerDeletedEventHandler;

    @Inject
    PoolServerContainerCreatedEventHandlerImplTestInterface poolServerContainerCreatedEventHandler;

    @Inject
    PoolServiceTestInterface poolService;

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
        final var poolServerContainer = testDataFactory.getPoolTestDataFactory()
                .createPoolServerContainer(defaultPoolServer, lobbyRuntime);


        final var poolId = poolServerContainer.getPoolId();
        final var serverId = poolServerContainer.getServerId();
        final var id = poolServerContainer.getId();

        final var eventBody1 = new PoolServerContainerCreatedEventBodyModel(poolId, serverId, id);
        final var eventModel1 = eventModelFactory.create(eventBody1);
        poolServerContainerCreatedEventHandler.handle(eventModel1);


        final var deletePoolServerContainerRequest = new DeletePoolServerContainerRequest(poolId, serverId, id);
        poolService.deletePoolServerContainer(deletePoolServerContainerRequest);

        final var eventBody2 = new PoolServerContainerDeletedEventBodyModel(poolId, serverId, id);
        final var eventModel2 = eventModelFactory.create(eventBody2);

        poolServerContainerDeletedEventHandler.handle(eventModel2);
        log.info("Retry");
        poolServerContainerDeletedEventHandler.handle(eventModel2);
    }
}