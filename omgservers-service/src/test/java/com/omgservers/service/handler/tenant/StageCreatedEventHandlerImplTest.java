package com.omgservers.service.handler.tenant;

import com.omgservers.schema.event.body.module.tenant.StageCreatedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.tenant.testInterface.StageCreatedEventHandlerImplTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class StageCreatedEventHandlerImplTest extends Assertions {

    @Inject
    StageCreatedEventHandlerImplTestInterface stageCreatedEventHandler;

    @Inject
    EventModelFactory eventModelFactory;

    @Inject
    TestDataFactory testDataFactory;

    @Test
    void givenHandler_whenRetry_thenFinished() {
        final var tenant = testDataFactory.getTenantTestDataFactory().createTenant();
        final var project = testDataFactory.getTenantTestDataFactory().createProject(tenant);
        final var stage = testDataFactory.getTenantTestDataFactory().createStage(project);

        final var tenantId = stage.getTenantId();
        final var id = stage.getId();

        final var eventBody = new StageCreatedEventBodyModel(tenantId, id);
        final var eventModel = eventModelFactory.create(eventBody);

        stageCreatedEventHandler.handle(eventModel);
        log.info("Retry");
        stageCreatedEventHandler.handle(eventModel);
    }
}