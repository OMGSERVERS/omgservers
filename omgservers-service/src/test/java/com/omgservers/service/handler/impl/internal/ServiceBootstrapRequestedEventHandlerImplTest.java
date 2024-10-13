package com.omgservers.service.handler.impl.internal;

import com.omgservers.BaseTestClass;
import com.omgservers.service.event.body.internal.ServiceBootstrapRequestedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.impl.internal.testInterface.ServiceBootstrapRequestedEventHandlerImplTestInterface;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class ServiceBootstrapRequestedEventHandlerImplTest extends BaseTestClass {

    @Inject
    ServiceBootstrapRequestedEventHandlerImplTestInterface serviceBootstrapRequestedEventHandler;

    @Inject
    EventModelFactory eventModelFactory;

    @Test
    void givenHandler_whenRetry_thenFinished() {
        final var eventBody = new ServiceBootstrapRequestedEventBodyModel();
        final var eventModel = eventModelFactory.create(eventBody);

        serviceBootstrapRequestedEventHandler.handle(eventModel);
        log.info("Retry");
        serviceBootstrapRequestedEventHandler.handle(eventModel);
    }
}