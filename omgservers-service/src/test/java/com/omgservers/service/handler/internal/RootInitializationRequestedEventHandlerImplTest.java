package com.omgservers.service.handler.internal;

import com.omgservers.model.event.body.internal.RootInitializationRequestedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.internal.testInterface.RootInitializationRequestedEventHandlerImplTestInterface;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class RootInitializationRequestedEventHandlerImplTest extends Assertions {

    @Inject
    RootInitializationRequestedEventHandlerImplTestInterface rootInitializationRequestedEventHandler;

    @Inject
    GetConfigOperation getConfigOperation;

    @Inject
    EventModelFactory eventModelFactory;

    @Test
    void givenHandler_whenRetry_thenFinished() {
        final var rootId = getConfigOperation.getServiceConfig().bootstrap().rootId();
        final var eventBody = new RootInitializationRequestedEventBodyModel(rootId);
        final var eventModel = eventModelFactory.create(eventBody);

        rootInitializationRequestedEventHandler.handle(eventModel);
        log.info("Retry");
        rootInitializationRequestedEventHandler.handle(eventModel);
    }
}