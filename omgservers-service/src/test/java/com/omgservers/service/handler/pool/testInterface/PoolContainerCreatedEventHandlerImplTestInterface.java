package com.omgservers.service.handler.pool.testInterface;

import com.omgservers.service.event.EventModel;
import com.omgservers.service.handler.impl.pool.PoolContainerCreatedEventHandlerImpl;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class PoolContainerCreatedEventHandlerImplTestInterface {
    private static final long TIMEOUT = 10L;

    final PoolContainerCreatedEventHandlerImpl handler;

    public void handle(final EventModel event) {
        handler.handle(event)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
