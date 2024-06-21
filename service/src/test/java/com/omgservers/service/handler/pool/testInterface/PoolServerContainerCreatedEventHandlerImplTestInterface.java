package com.omgservers.service.handler.pool.testInterface;

import com.omgservers.model.event.EventModel;
import com.omgservers.service.handler.pool.PoolServerContainerCreatedEventHandlerImpl;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class PoolServerContainerCreatedEventHandlerImplTestInterface {
    private static final long TIMEOUT = 10L;

    final PoolServerContainerCreatedEventHandlerImpl poolServerContainerCreatedEventHandler;

    public void handle(final EventModel event) {
        poolServerContainerCreatedEventHandler.handle(event)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
