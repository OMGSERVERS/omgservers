package com.omgservers.service.handler.pool;

import com.omgservers.model.event.body.module.pool.PoolRequestCreatedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.pool.testInterface.PoolRequestCreatedEventHandlerImplTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class PoolRequestCreatedEventHandlerImplTest extends Assertions {

    @Inject
    PoolRequestCreatedEventHandlerImplTestInterface poolRequestCreatedEventHandler;

    @Inject
    EventModelFactory eventModelFactory;

    @Inject
    TestDataFactory testDataFactory;

    @Test
    void givenHandler_whenRetry_thenFinished() {
        final var defaultPool = testDataFactory.getPoolTestDataFactory().createDefaultPool();
        final var tenant = testDataFactory.getTenantTestDataFactory().createTenant();
        final var project = testDataFactory.getTenantTestDataFactory().createProject(tenant);
        final var stage = testDataFactory.getTenantTestDataFactory().createStage(project);
        final var version = testDataFactory.getTenantTestDataFactory().createVersion(stage);
        final var lobby = testDataFactory.getLobbyTestDataFactory().createLobby(version);
        final var lobbyRuntime = testDataFactory.getRuntimeTestDataFactory()
                .createLobbyRuntime(tenant, version, lobby);
        final var poolRequest = testDataFactory.getPoolTestDataFactory().createPoolRequest(defaultPool, lobbyRuntime);

        final var poolId = poolRequest.getPoolId();
        final var id = poolRequest.getId();

        final var eventBody = new PoolRequestCreatedEventBodyModel(poolId, id);
        final var eventModel = eventModelFactory.create(eventBody);

        poolRequestCreatedEventHandler.handle(eventModel);
        log.info("Retry");
        poolRequestCreatedEventHandler.handle(eventModel);
    }
}