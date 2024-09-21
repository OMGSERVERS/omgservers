package com.omgservers.service.handler.tenant;

import com.omgservers.schema.module.tenant.tenantJenkinsRequest.DeleteTenantJenkinsRequestRequest;
import com.omgservers.service.event.body.module.tenant.TenantJenkinsRequestDeletedEventBodyModel;
import com.omgservers.schema.model.tenantJenkinsRequest.TenantJenkinsRequestQualifierEnum;
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
class TenantJenkinsRequestDeletedEventHandlerImplTest extends Assertions {

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
                        TenantJenkinsRequestQualifierEnum.LUAJIT_RUNTIME_BUILDER_V1, 1);

        final var tenantId = versionJenkinsRequest.getTenantId();
        final var id = versionJenkinsRequest.getId();

        final var deleteVersionJenkinsRequestRequest = new DeleteTenantJenkinsRequestRequest(tenantId, id);
        versionServiceTestInterface.deleteVersionJenkinsRequest(deleteVersionJenkinsRequestRequest);

        final var eventBody = new TenantJenkinsRequestDeletedEventBodyModel(tenantId, id);
        final var eventModel = eventModelFactory.create(eventBody);

        versionJenkinsRequestDeletedEventHandlerImplTestInterface.handle(eventModel);
        log.info("Retry");
        versionJenkinsRequestDeletedEventHandlerImplTestInterface.handle(eventModel);
    }
}