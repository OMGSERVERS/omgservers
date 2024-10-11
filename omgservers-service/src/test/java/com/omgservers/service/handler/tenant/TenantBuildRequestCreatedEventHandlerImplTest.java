package com.omgservers.service.handler.tenant;

import com.omgservers.schema.model.tenantBuildRequest.TenantBuildRequestQualifierEnum;
import com.omgservers.service.event.body.module.tenant.TenantBuildRequestCreatedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.tenant.testInterface.TenantBuildRequestCreatedEventHandlerImplTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class TenantBuildRequestCreatedEventHandlerImplTest extends Assertions {

    @Inject
    TenantBuildRequestCreatedEventHandlerImplTestInterface tenantBuildRequestCreatedEventHandlerImplTestInterface;

    @Inject
    EventModelFactory eventModelFactory;

    @Inject
    TestDataFactory testDataFactory;

    @Test
    void givenHandler_whenRetry_thenFinished() {
        final var tenant = testDataFactory.getTenantTestDataFactory().createTenant();
        final var project = testDataFactory.getTenantTestDataFactory().createTenantProject(tenant);
        final var version = testDataFactory.getTenantTestDataFactory().createTenantVersion(project);
        final var tenantBuildRequest = testDataFactory.getTenantTestDataFactory()
                .createTenantBuildRequest(version,
                        TenantBuildRequestQualifierEnum.JENKINS_LUAJIT_RUNTIME_BUILDER_V1, 1);

        final var tenantId = tenantBuildRequest.getTenantId();
        final var id = tenantBuildRequest.getId();

        final var eventBody = new TenantBuildRequestCreatedEventBodyModel(tenantId, id);
        final var eventModel = eventModelFactory.create(eventBody);

        tenantBuildRequestCreatedEventHandlerImplTestInterface.handle(eventModel);
        log.info("Retry");
        tenantBuildRequestCreatedEventHandlerImplTestInterface.handle(eventModel);
    }
}