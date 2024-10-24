package com.omgservers.service.handler.impl.pool.testInterface;

import com.omgservers.service.event.EventModel;
import com.omgservers.service.handler.impl.pool.PoolContainerDeletedEventHandlerImpl;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class PoolContainerDeletedEventHandlerImplTestInterface {
    private static final long TIMEOUT = 10L;

    final PoolContainerDeletedEventHandlerImpl handler;

    public void handle(final EventModel event) {
        handler.handle(event)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
