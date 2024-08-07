package com.omgservers.service.handler.pool.testInterface;

import com.omgservers.schema.event.EventModel;
import com.omgservers.service.handler.pool.PoolServerContainerDeletedEventHandlerImpl;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class PoolServerContainerDeletedEventHandlerImplTestInterface {
    private static final long TIMEOUT = 10L;

    final PoolServerContainerDeletedEventHandlerImpl poolServerContainerDeletedEventHandler;

    public void handle(final EventModel event) {
        poolServerContainerDeletedEventHandler.handle(event)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
