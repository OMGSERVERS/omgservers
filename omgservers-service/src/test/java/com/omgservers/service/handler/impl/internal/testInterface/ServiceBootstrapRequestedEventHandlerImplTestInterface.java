package com.omgservers.service.handler.impl.internal.testInterface;

import com.omgservers.service.event.EventModel;
import com.omgservers.service.handler.impl.internal.ServiceBootstrapRequestedEventHandlerImpl;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ServiceBootstrapRequestedEventHandlerImplTestInterface {
    private static final long TIMEOUT = 2L;

    final ServiceBootstrapRequestedEventHandlerImpl serviceBootstrapRequestedEventHandler;

    public void handle(final EventModel event) {
        serviceBootstrapRequestedEventHandler.handle(event)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
