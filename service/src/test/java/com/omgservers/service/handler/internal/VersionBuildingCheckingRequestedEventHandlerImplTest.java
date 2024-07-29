package com.omgservers.service.handler.internal;

import com.omgservers.schema.module.jenkins.GetLuaJitWorkerBuilderV1Request;
import com.omgservers.schema.module.jenkins.GetLuaJitWorkerBuilderV1Response;
import com.omgservers.schema.event.body.internal.VersionBuildingCheckingRequestedEventBodyModel;
import com.omgservers.schema.model.versionJenkinsRequest.VersionJenkinsRequestQualifierEnum;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.internal.testInterface.VersionBuildingCheckingRequestedEventHandlerImplTestInterface;
import com.omgservers.service.integration.jenkins.impl.service.jenkinsService.JenkinsService;
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
class VersionBuildingCheckingRequestedEventHandlerImplTest extends Assertions {

    @Inject
    VersionBuildingCheckingRequestedEventHandlerImplTestInterface versionBuildingCheckingRequestedEventHandler;

    @Inject
    EventModelFactory eventModelFactory;

    @Inject
    TestDataFactory testDataFactory;

    @InjectMock
    JenkinsService jenkinsService;

    @Test
    void givenHandler_whenRetry_thenFinished() {
        final var buildNumber = 1;
        Mockito.when(jenkinsService.getLuaJitWorkerBuilderV1(new GetLuaJitWorkerBuilderV1Request(buildNumber)))
                .thenReturn(Uni.createFrom().item(new GetLuaJitWorkerBuilderV1Response("mock-image:latest")));

        final var tenant = testDataFactory.getTenantTestDataFactory().createTenant();
        final var project = testDataFactory.getTenantTestDataFactory().createProject(tenant);
        final var stage = testDataFactory.getTenantTestDataFactory().createStage(project);
        final var version = testDataFactory.getTenantTestDataFactory().createVersion(stage);
        final var versionJenkinsRequest = testDataFactory.getTenantTestDataFactory()
                .createVersionJenkinsRequest(version, VersionJenkinsRequestQualifierEnum.LUAJIT_WORKER_BUILDER_V1,
                        buildNumber);

        final var eventBody = new VersionBuildingCheckingRequestedEventBodyModel(tenant.getId(),
                version.getId(), 1);
        final var eventModel = eventModelFactory.create(eventBody);

        versionBuildingCheckingRequestedEventHandler.handle(eventModel);
        log.info("Retry");
        versionBuildingCheckingRequestedEventHandler.handle(eventModel);
    }
}