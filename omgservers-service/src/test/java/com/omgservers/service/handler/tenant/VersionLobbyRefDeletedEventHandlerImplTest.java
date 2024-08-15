package com.omgservers.service.handler.tenant;

import com.omgservers.schema.module.tenant.DeleteVersionLobbyRefRequest;
import com.omgservers.service.event.body.module.tenant.VersionLobbyRefDeletedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.tenant.testInterface.VersionLobbyRefDeletedEventHandlerImplTestInterface;
import com.omgservers.service.module.tenant.impl.service.versionService.testInterface.VersionServiceTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class VersionLobbyRefDeletedEventHandlerImplTest extends Assertions {

    @Inject
    VersionLobbyRefDeletedEventHandlerImplTestInterface versionLobbyRefDeletedEventHandler;

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
        final var lobby = testDataFactory.getLobbyTestDataFactory().createLobby(versionLobbyRequest);
        final var versionLobbyRef = testDataFactory.getTenantTestDataFactory()
                .createVersionLobbyRef(version, lobby);

        final var tenantId = versionLobbyRef.getTenantId();
        final var id = versionLobbyRef.getId();

        final var deleteVersionLobbyRefRequest = new DeleteVersionLobbyRefRequest(tenantId, id);
        versionService.deleteVersionLobbyRef(deleteVersionLobbyRefRequest);

        final var eventBody = new VersionLobbyRefDeletedEventBodyModel(tenantId, id);
        final var eventModel = eventModelFactory.create(eventBody);

        versionLobbyRefDeletedEventHandler.handle(eventModel);
        log.info("Retry");
        versionLobbyRefDeletedEventHandler.handle(eventModel);
    }
}