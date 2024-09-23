package com.omgservers.service.handler.internal;

import com.omgservers.service.event.body.internal.RuntimeDeploymentRequestedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.internal.testInterface.RuntimeDeploymentRequestedEventHandlerImplTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class RuntimeDeploymentRequestedEventHandlerImplTest extends Assertions {

    @Inject
    RuntimeDeploymentRequestedEventHandlerImplTestInterface runtimeDeploymentRequestedEventHandler;

    @Inject
    EventModelFactory eventModelFactory;

    @Inject
    TestDataFactory testDataFactory;

    @Test
    void givenHandler_whenRetry_thenFinished() {
        final var defaultPool = testDataFactory.getPoolTestDataFactory().createDefaultPool();
        final var tenant = testDataFactory.getTenantTestDataFactory().createTenant();
        final var tenantProject = testDataFactory.getTenantTestDataFactory().createTenantProject(tenant);
        final var tenantStage = testDataFactory.getTenantTestDataFactory().createStage(tenantProject);
        final var tenantVersion = testDataFactory.getTenantTestDataFactory().createTenantVersion(tenantProject);
        final var tenantDeployment = testDataFactory.getTenantTestDataFactory()
                .createTenantDeployment(tenantStage, tenantVersion);
        final var tenantImageRef = testDataFactory.getTenantTestDataFactory()
                .createTenantImageRef(tenantVersion);
        final var lobby = testDataFactory.getLobbyTestDataFactory().createLobby(tenantDeployment);
        final var lobbyRuntime = testDataFactory.getRuntimeTestDataFactory()
                .createLobbyRuntime(tenant, tenantDeployment, lobby);

        final var runtimeId = lobbyRuntime.getId();

        final var eventBody = new RuntimeDeploymentRequestedEventBodyModel(runtimeId);
        final var eventModel = eventModelFactory.create(eventBody);

        runtimeDeploymentRequestedEventHandler.handle(eventModel);
        log.info("Retry");
        runtimeDeploymentRequestedEventHandler.handle(eventModel);
    }
}