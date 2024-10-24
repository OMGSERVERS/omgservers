package com.omgservers.service.handler.impl.pool;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.module.pool.poolContainer.DeletePoolContainerRequest;
import com.omgservers.service.event.body.module.pool.PoolContainerCreatedEventBodyModel;
import com.omgservers.service.event.body.module.pool.PoolContainerDeletedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.impl.pool.testInterface.PoolContainerCreatedEventHandlerImplTestInterface;
import com.omgservers.service.handler.impl.pool.testInterface.PoolContainerDeletedEventHandlerImplTestInterface;
import com.omgservers.service.module.pool.impl.service.poolService.testInterface.PoolServiceTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class PoolContainerDeletedEventHandlerImplTest extends BaseTestClass {

    @Inject
    PoolContainerDeletedEventHandlerImplTestInterface poolContainerDeletedEventHandler;

    @Inject
    PoolContainerCreatedEventHandlerImplTestInterface poolContainerCreatedEventHandler;

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
        final var poolContainer = testDataFactory.getPoolTestDataFactory()
                .createPoolContainer(defaultPoolServer, lobbyRuntime);


        final var poolId = poolContainer.getPoolId();
        final var serverId = poolContainer.getServerId();
        final var id = poolContainer.getId();

        final var eventBody1 = new PoolContainerCreatedEventBodyModel(poolId, serverId, id);
        final var eventModel1 = eventModelFactory.create(eventBody1);
        poolContainerCreatedEventHandler.handle(eventModel1);


        final var deletePoolContainerRequest = new DeletePoolContainerRequest(poolId, serverId, id);
        poolService.execute(deletePoolContainerRequest);

        final var eventBody2 = new PoolContainerDeletedEventBodyModel(poolId, serverId, id);
        final var eventModel2 = eventModelFactory.create(eventBody2);

        poolContainerDeletedEventHandler.handle(eventModel2);
        log.info("Retry");
        poolContainerDeletedEventHandler.handle(eventModel2);
    }
}