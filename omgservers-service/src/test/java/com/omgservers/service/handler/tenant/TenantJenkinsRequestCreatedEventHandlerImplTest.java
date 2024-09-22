package com.omgservers.service.handler.tenant;

import com.omgservers.schema.model.tenantJenkinsRequest.TenantJenkinsRequestQualifierEnum;
import com.omgservers.service.event.body.module.tenant.TenantJenkinsRequestCreatedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.tenant.testInterface.VersionJenkinsRequestCreatedEventHandlerImplTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class TenantJenkinsRequestCreatedEventHandlerImplTest extends Assertions {

    @Inject
    VersionJenkinsRequestCreatedEventHandlerImplTestInterface versionJenkinsRequestCreatedEventHandlerImplTestInterface;

    @Inject
    EventModelFactory eventModelFactory;

    @Inject
    TestDataFactory testDataFactory;

    @Test
    void givenHandler_whenRetry_thenFinished() {
        final var tenant = testDataFactory.getTenantTestDataFactory().createTenant();
        final var project = testDataFactory.getTenantTestDataFactory().createProject(tenant);
        final var version = testDataFactory.getTenantTestDataFactory().createVersion(project);
        final var versionJenkinsRequest = testDataFactory.getTenantTestDataFactory()
                .createVersionJenkinsRequest(version,
                        TenantJenkinsRequestQualifierEnum.LUAJIT_RUNTIME_BUILDER_V1, 1);

        final var tenantId = versionJenkinsRequest.getTenantId();
        final var id = versionJenkinsRequest.getId();

        final var eventBody = new TenantJenkinsRequestCreatedEventBodyModel(tenantId, id);
        final var eventModel = eventModelFactory.create(eventBody);

        versionJenkinsRequestCreatedEventHandlerImplTestInterface.handle(eventModel);
        log.info("Retry");
        versionJenkinsRequestCreatedEventHandlerImplTestInterface.handle(eventModel);
    }
}