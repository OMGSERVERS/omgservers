package com.omgservers.service.handler.pool.testInterface;

import com.omgservers.service.event.EventModel;
import com.omgservers.service.handler.pool.PoolServerCreatedEventHandlerImpl;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class PoolServerCreatedEventHandlerImplTestInterface {
    private static final long TIMEOUT = 1L;

    final PoolServerCreatedEventHandlerImpl poolServerCreatedEventHandler;

    public void handle(final EventModel event) {
        poolServerCreatedEventHandler.handle(event)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
