package com.omgservers.service.handler.pool;

import com.omgservers.schema.module.pool.poolRequest.DeletePoolRequestRequest;
import com.omgservers.service.event.body.module.pool.PoolRequestDeletedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.pool.testInterface.PoolRequestDeletedEventHandlerImplTestInterface;
import com.omgservers.service.module.pool.impl.service.poolService.testInterface.PoolServiceTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class PoolRequestDeletedEventHandlerImplTest extends Assertions {

    @Inject
    PoolRequestDeletedEventHandlerImplTestInterface poolRequestDeletedEventHandler;

    @Inject
    PoolServiceTestInterface poolService;

    @Inject
    EventModelFactory eventModelFactory;

    @Inject
    TestDataFactory testDataFactory;

    @Test
    void givenHandler_whenRetry_thenFinished() {
        final var defaultPool = testDataFactory.getPoolTestDataFactory().createDefaultPool();
        final var tenant = testDataFactory.getTenantTestDataFactory().createTenant();
        final var project = testDataFactory.getTenantTestDataFactory().createTenantProject(tenant);
        final var stage = testDataFactory.getTenantTestDataFactory().createStage(project);
        final var version = testDataFactory.getTenantTestDataFactory().createTenantVersion(project);
        final var tenantDeployment = testDataFactory.getTenantTestDataFactory().createTenantDeployment(stage, version);
        final var lobby = testDataFactory.getLobbyTestDataFactory().createLobby(tenantDeployment);
        final var lobbyRuntime = testDataFactory.getRuntimeTestDataFactory()
                .createLobbyRuntime(tenant, tenantDeployment, lobby);
        final var poolRequest = testDataFactory.getPoolTestDataFactory().createPoolRequest(defaultPool, lobbyRuntime);

        final var poolId = poolRequest.getPoolId();
        final var id = poolRequest.getId();

        final var deletePoolRequestRequest = new DeletePoolRequestRequest(poolId, id);
        poolService.deletePoolRequest(deletePoolRequestRequest);

        final var eventBody = new PoolRequestDeletedEventBodyModel(poolId, id);
        final var eventModel = eventModelFactory.create(eventBody);

        poolRequestDeletedEventHandler.handle(eventModel);
        log.info("Retry");
        poolRequestDeletedEventHandler.handle(eventModel);
    }
}