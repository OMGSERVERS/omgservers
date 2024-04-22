package com.omgservers.service.handler.tenant;

import com.omgservers.model.dto.tenant.versionJenkinsRequest.DeleteVersionJenkinsRequestRequest;
import com.omgservers.model.event.body.module.tenant.VersionJenkinsRequestDeletedEventBodyModel;
import com.omgservers.model.versionJenkinsRequest.VersionJenkinsRequestQualifierEnum;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.tenant.testInterface.VersionJenkinsRequestDeletedEventHandlerImplTestInterface;
import com.omgservers.service.module.tenant.impl.service.versionService.testInterface.VersionServiceTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class VersionJenkinsRequestDeletedEventHandlerImplTest extends Assertions {

    @Inject
    VersionJenkinsRequestDeletedEventHandlerImplTestInterface versionJenkinsRequestDeletedEventHandlerImplTestInterface;

    @Inject
    VersionServiceTestInterface versionServiceTestInterface;

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
        final var versionJenkinsRequest = testDataFactory.getTenantTestDataFactory()
                .createVersionJenkinsRequest(version,
                        VersionJenkinsRequestQualifierEnum.LUAJIT_WORKER_BUILDER_V1, 1);

        final var tenantId = versionJenkinsRequest.getTenantId();
        final var id = versionJenkinsRequest.getId();

        final var deleteVersionJenkinsRequestRequest = new DeleteVersionJenkinsRequestRequest(tenantId, id);
        versionServiceTestInterface.deleteVersionJenkinsRequest(deleteVersionJenkinsRequestRequest);

        final var eventBody = new VersionJenkinsRequestDeletedEventBodyModel(tenantId, id);
        final var eventModel = eventModelFactory.create(eventBody);

        versionJenkinsRequestDeletedEventHandlerImplTestInterface.handle(eventModel);
        log.info("Retry");
        versionJenkinsRequestDeletedEventHandlerImplTestInterface.handle(eventModel);
    }
}