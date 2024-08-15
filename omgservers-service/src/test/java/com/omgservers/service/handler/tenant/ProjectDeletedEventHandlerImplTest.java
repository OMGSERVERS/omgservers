package com.omgservers.service.handler.tenant;

import com.omgservers.schema.module.tenant.DeleteProjectRequest;
import com.omgservers.service.event.body.module.tenant.ProjectDeletedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.tenant.testInterface.ProjectDeletedEventHandlerImplTestInterface;
import com.omgservers.service.module.tenant.impl.service.projectService.testInterface.ProjectServiceTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class ProjectDeletedEventHandlerImplTest extends Assertions {

    @Inject
    ProjectDeletedEventHandlerImplTestInterface projectDeletedEventHandler;

    @Inject
    ProjectServiceTestInterface projectService;

    @Inject
    EventModelFactory eventModelFactory;

    @Inject
    TestDataFactory testDataFactory;

    @Test
    void givenHandler_whenRetry_thenFinished() {
        final var tenant = testDataFactory.getTenantTestDataFactory().createTenant();
        final var project = testDataFactory.getTenantTestDataFactory().createProject(tenant);

        final var tenantId = project.getTenantId();
        final var id = project.getId();

        final var deleteProjectRequest = new DeleteProjectRequest(tenantId, id);
        projectService.deleteProject(deleteProjectRequest);

        final var eventBody = new ProjectDeletedEventBodyModel(tenantId, id);
        final var eventModel = eventModelFactory.create(eventBody);

        projectDeletedEventHandler.handle(eventModel);
        log.info("Retry");
        projectDeletedEventHandler.handle(eventModel);
    }
}