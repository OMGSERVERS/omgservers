package com.omgservers.service.handler.pool.testInterface;

import com.omgservers.model.event.EventModel;
import com.omgservers.service.handler.pool.PoolServerDeletedEventHandlerImpl;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class PoolServerDeletedEventHandlerImplTestInterface {
    private static final long TIMEOUT = 1L;

    final PoolServerDeletedEventHandlerImpl poolServerDeletedEventHandler;

    public void handle(final EventModel event) {
        poolServerDeletedEventHandler.handle(event)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
