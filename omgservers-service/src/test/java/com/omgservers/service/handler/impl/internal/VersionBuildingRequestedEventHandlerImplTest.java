package com.omgservers.service.handler.impl.internal;

import com.omgservers.BaseTestClass;
import com.omgservers.service.event.body.internal.VersionBuildingRequestedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.impl.internal.testInterface.VersionBuildingRequestedEventHandlerImplTestInterface;
import com.omgservers.service.service.jenkins.JenkinsService;
import com.omgservers.service.service.jenkins.dto.RunLuaJitRuntimeBuilderV1Response;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@Slf4j
@QuarkusTest
class VersionBuildingRequestedEventHandlerImplTest extends BaseTestClass {

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
        Mockito.when(jenkinsService.runLuaJitRuntimeBuilderV1(Mockito.any()))
                .thenReturn(Uni.createFrom().item(new RunLuaJitRuntimeBuilderV1Response(1)));

        final var tenant = testDataFactory.getTenantTestDataFactory().createTenant();
        final var tenantProject = testDataFactory.getTenantTestDataFactory()
                .createTenantProject(tenant);
        final var tenantStage = testDataFactory.getTenantTestDataFactory()
                .createStage(tenantProject);
        final var tenantVersion = testDataFactory.getTenantTestDataFactory()
                .createTenantVersion(tenantProject);
        final var tenantFilesArchive = testDataFactory.getTenantTestDataFactory()
                .createTenantFilesArchive(tenantVersion);

        final var eventBody = new VersionBuildingRequestedEventBodyModel(tenant.getId(), tenantVersion.getId());
        final var eventModel = eventModelFactory.create(eventBody);

        versionBuildingRequestedEventHandlerImplTestInterface.handle(eventModel);
        log.info("Retry");
        versionBuildingRequestedEventHandlerImplTestInterface.handle(eventModel);
    }
}