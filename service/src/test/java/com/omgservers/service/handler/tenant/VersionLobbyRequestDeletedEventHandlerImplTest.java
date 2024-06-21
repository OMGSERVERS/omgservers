package com.omgservers.service.handler.tenant;

import com.omgservers.model.dto.tenant.DeleteVersionLobbyRequestRequest;
import com.omgservers.model.event.body.module.tenant.VersionLobbyRequestDeletedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.tenant.testInterface.VersionLobbyRequestDeletedEventHandlerImplTestInterface;
import com.omgservers.service.module.tenant.impl.service.versionService.testInterface.VersionServiceTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class VersionLobbyRequestDeletedEventHandlerImplTest extends Assertions {

    @Inject
    VersionLobbyRequestDeletedEventHandlerImplTestInterface versionLobbyRequestDeletedEventHandler;

    @Inject
    VersionServiceTestInterface versionService;

    @Inject
    EventModelFactory eventModelFactory;

    @Inject
    TestDataFactory testDataFactory;

    @Test
    void givenHandler_whenRetry_thenFinished() {
        final var tenant = testDataFactory.getTenantTestDataFactory().createTenant();
        final var project = testDataFactory.getTenantTestDataFactory().createProject(tenant);
        final var stage = testDataFactory.getTenantTestDataFactory().createStage(project);
        final var version = testDataFactory.getTenantTestDataFactory().createVersion(stage);
        final var versionLobbyRequest = testDataFactory.getTenantTestDataFactory()
                .createVersionLobbyRequest(version);

        final var tenantId = versionLobbyRequest.getTenantId();
        final var id = versionLobbyRequest.getId();

        final var deleteVersionLobbyRequestRequest = new DeleteVersionLobbyRequestRequest(tenantId, id);
        versionService.deleteVersionLobbyRequest(deleteVersionLobbyRequestRequest);

        final var eventBody = new VersionLobbyRequestDeletedEventBodyModel(tenantId, id);
        final var eventModel = eventModelFactory.create(eventBody);

        versionLobbyRequestDeletedEventHandler.handle(eventModel);
        log.info("Retry");
        versionLobbyRequestDeletedEventHandler.handle(eventModel);
    }
}