package com.omgservers;

import com.omgservers.service.event.body.internal.ServiceBootstrapRequestedEventBodyModel;
import com.omgservers.service.factory.system.EventModelFactory;
import com.omgservers.service.handler.impl.internal.testInterface.ServiceBootstrapRequestedEventHandlerImplTestInterface;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

@Slf4j
@QuarkusTest
public class BaseTestClass extends Assertions {

    @Inject
    ServiceBootstrapRequestedEventHandlerImplTestInterface serviceBootstrapRequestedEventHandler;

    @Inject
    EventModelFactory eventModelFactory;

    @BeforeEach
    void beforeEach() {
        serviceBootstrapRequestedEventHandler.handle(eventModelFactory
                .create(new ServiceBootstrapRequestedEventBodyModel()));
    }
}
