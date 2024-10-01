package com.omgservers.service.handler.pool.testInterface;

import com.omgservers.service.event.EventModel;
import com.omgservers.service.handler.impl.pool.PoolRequestDeletedEventHandlerImpl;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class PoolRequestDeletedEventHandlerImplTestInterface {
    private static final long TIMEOUT = 1L;

    final PoolRequestDeletedEventHandlerImpl poolRequestDeletedEventHandler;

    public void handle(final EventModel event) {
        poolRequestDeletedEventHandler.handle(event)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
