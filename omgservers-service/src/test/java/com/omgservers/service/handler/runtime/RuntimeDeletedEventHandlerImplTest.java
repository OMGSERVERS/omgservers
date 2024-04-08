package com.omgservers.service.handler.runtime;

import com.omgservers.model.dto.runtime.DeleteRuntimeRequest;
import com.omgservers.model.event.body.module.runtime.RuntimeDeletedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.runtime.testInterface.RuntimeDeletedEventHandlerImplTestInterface;
import com.omgservers.service.module.runtime.impl.service.runtimeService.testInterface.RuntimeServiceTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class RuntimeDeletedEventHandlerImplTest extends Assertions {

    @Inject
    RuntimeDeletedEventHandlerImplTestInterface runtimeDeletedEventHandler;

    @Inject
    RuntimeServiceTestInterface runtimeService;

    @Inject
    EventModelFactory eventModelFactory;

    @Inject
    TestDataFactory testDataFactory;

    @Test
    void givenHandler_whenRetry_thenFinished() {
        final var root = testDataFactory.getRootTestDataFactory().createRoot();
        final var defaultPool = testDataFactory.getPoolTestDataFactory().createDefaultPool(root);
        final var tenant = testDataFactory.getTenantTestDataFactory().createTenant();
        final var project = testDataFactory.getTenantTestDataFactory().createProject(tenant);
        final var stage = testDataFactory.getTenantTestDataFactory().createStage(project);
        final var version = testDataFactory.getTenantTestDataFactory().createVersion(stage);
        final var lobby = testDataFactory.getLobbyTestDataFactory().createLobby(version);
        final var lobbyRuntime = testDataFactory.getRuntimeTestDataFactory()
                .createLobbyRuntime(tenant, version, lobby);

        final var runtimeId = lobbyRuntime.getId();

        final var deleteRuntimeRequest = new DeleteRuntimeRequest(runtimeId);
        runtimeService.deleteRuntime(deleteRuntimeRequest);

        final var eventBody = new RuntimeDeletedEventBodyModel(runtimeId);
        final var eventModel = eventModelFactory.create(eventBody);

        runtimeDeletedEventHandler.handle(eventModel);
        log.info("Retry");
        runtimeDeletedEventHandler.handle(eventModel);
    }
}