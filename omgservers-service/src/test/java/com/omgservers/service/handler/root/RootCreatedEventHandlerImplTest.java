package com.omgservers.service.handler.root;

import com.omgservers.service.event.body.module.root.RootCreatedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.root.testInterface.RootCreatedEventHandlerImplTestInterface;
import com.omgservers.service.module.root.impl.service.rootService.testInterface.RootServiceTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class RootCreatedEventHandlerImplTest extends Assertions {

    @Inject
    RootCreatedEventHandlerImplTestInterface rootCreatedEventHandler;

    @Inject
    RootServiceTestInterface rootService;

    @Inject
    EventModelFactory eventModelFactory;

    @Inject
    TestDataFactory testDataFactory;

    @Test
    void givenHandler_whenRetry_thenFinished() {
        final var root = testDataFactory.getRootTestDataFactory().createRoot();

        final var eventBody = new RootCreatedEventBodyModel(root.getId());
        final var eventModel = eventModelFactory.create(eventBody);

        rootCreatedEventHandler.handle(eventModel);
        log.info("Retry");
        rootCreatedEventHandler.handle(eventModel);
    }
}