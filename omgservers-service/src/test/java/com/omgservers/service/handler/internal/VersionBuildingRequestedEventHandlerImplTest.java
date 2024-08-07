package com.omgservers.service.handler.internal;

import com.omgservers.schema.module.jenkins.RunLuaJitWorkerBuilderV1Response;
import com.omgservers.schema.event.body.internal.VersionBuildingRequestedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.internal.testInterface.VersionBuildingRequestedEventHandlerImplTestInterface;
import com.omgservers.service.server.service.jenkins.JenkinsService;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@Slf4j
@QuarkusTest
class VersionBuildingRequestedEventHandlerImplTest extends Assertions {

    @Inject
    VersionBuildingRequestedEventHandlerImplTestInterface versionBuildingRequestedEventHandlerImplTestInterface;

    @Inject
    EventModelFactory eventModelFactory;

    @Inject
    TestDataFactory testDataFactory;

    @InjectMock
    JenkinsService jenkinsService;

    @Test
    void givenHandler_whenRetry_thenFinished() {
        Mockito.when(jenkinsService.runLuaJitWorkerBuilderV1(Mockito.any()))
                .thenReturn(Uni.createFrom().item(new RunLuaJitWorkerBuilderV1Response(1)));

        final var tenant = testDataFactory.getTenantTestDataFactory().createTenant();
        final var project = testDataFactory.getTenantTestDataFactory().createProject(tenant);
        final var stage = testDataFactory.getTenantTestDataFactory().createStage(project);
        final var version = testDataFactory.getTenantTestDataFactory().createVersion(stage);

        final var eventBody = new VersionBuildingRequestedEventBodyModel(tenant.getId(), version.getId());
        final var eventModel = eventModelFactory.create(eventBody);

        versionBuildingRequestedEventHandlerImplTestInterface.handle(eventModel);
        log.info("Retry");
        versionBuildingRequestedEventHandlerImplTestInterface.handle(eventModel);
    }
}