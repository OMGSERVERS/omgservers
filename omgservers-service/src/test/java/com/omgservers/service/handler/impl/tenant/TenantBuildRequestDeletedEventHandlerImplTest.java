package com.omgservers.service.handler.impl.tenant;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.model.tenantBuildRequest.TenantBuildRequestQualifierEnum;
import com.omgservers.schema.module.tenant.tenantBuildRequest.DeleteTenantBuildRequestRequest;
import com.omgservers.service.event.body.module.tenant.TenantBuildRequestDeletedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.impl.tenant.testInterface.TenantBuildRequestDeletedEventHandlerImplTestInterface;
import com.omgservers.service.shard.tenant.impl.service.tenantService.testInterface.TenantServiceTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class TenantBuildRequestDeletedEventHandlerImplTest extends BaseTestClass {

    @Inject
    TenantBuildRequestDeletedEventHandlerImplTestInterface tenantBuildRequestDeletedEventHandlerImplTestInterface;

    @Inject
    TenantServiceTestInterface tenantService;

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

        final var deleteTenantBuildRequestRequest = new DeleteTenantBuildRequestRequest(tenantId, id);
        tenantService.deleteTenantBuildRequest(deleteTenantBuildRequestRequest);

        final var eventBody = new TenantBuildRequestDeletedEventBodyModel(tenantId, id);
        final var eventModel = eventModelFactory.create(eventBody);

        tenantBuildRequestDeletedEventHandlerImplTestInterface.handle(eventModel);
        log.info("Retry");
        tenantBuildRequestDeletedEventHandlerImplTestInterface.handle(eventModel);
    }
}