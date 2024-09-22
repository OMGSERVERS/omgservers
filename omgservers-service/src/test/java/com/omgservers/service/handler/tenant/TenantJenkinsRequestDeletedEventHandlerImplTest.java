package com.omgservers.service.handler.tenant;

import com.omgservers.schema.model.tenantJenkinsRequest.TenantJenkinsRequestQualifierEnum;
import com.omgservers.schema.module.tenant.tenantJenkinsRequest.DeleteTenantJenkinsRequestRequest;
import com.omgservers.service.event.body.module.tenant.TenantJenkinsRequestDeletedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.tenant.testInterface.TenantJenkinsRequestDeletedEventHandlerImplTestInterface;
import com.omgservers.service.module.tenant.impl.service.tenantService.testInterface.TenantServiceTestInterface;
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
    TenantJenkinsRequestDeletedEventHandlerImplTestInterface tenantJenkinsRequestDeletedEventHandlerImplTestInterface;

    @Inject
    TenantServiceTestInterface tenantService;

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

        final var deleteTenantJenkinsRequestRequest = new DeleteTenantJenkinsRequestRequest(tenantId, id);
        tenantService.deleteTenantJenkinsRequest(deleteTenantJenkinsRequestRequest);

        final var eventBody = new TenantJenkinsRequestDeletedEventBodyModel(tenantId, id);
        final var eventModel = eventModelFactory.create(eventBody);

        tenantJenkinsRequestDeletedEventHandlerImplTestInterface.handle(eventModel);
        log.info("Retry");
        tenantJenkinsRequestDeletedEventHandlerImplTestInterface.handle(eventModel);
    }
}