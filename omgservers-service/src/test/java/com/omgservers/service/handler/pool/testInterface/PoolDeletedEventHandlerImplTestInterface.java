package com.omgservers.service.handler.pool.testInterface;

import com.omgservers.service.event.EventModel;
import com.omgservers.service.handler.impl.pool.PoolDeletedEventHandlerImpl;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class PoolDeletedEventHandlerImplTestInterface {
    private static final long TIMEOUT = 1L;

    final PoolDeletedEventHandlerImpl poolDeletedEventHandler;

    public void handle(final EventModel event) {
        poolDeletedEventHandler.handle(event)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
