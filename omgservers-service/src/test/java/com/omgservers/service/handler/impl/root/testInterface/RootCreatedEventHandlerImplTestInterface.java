package com.omgservers.service.handler.impl.root.testInterface;

import com.omgservers.service.event.EventModel;
import com.omgservers.service.handler.impl.root.RootCreatedEventHandlerImpl;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class RootCreatedEventHandlerImplTestInterface {
    private static final long TIMEOUT = 1L;

    final RootCreatedEventHandlerImpl rootCreatedEventHandler;

    public void handle(final EventModel event) {
        rootCreatedEventHandler.handle(event)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
