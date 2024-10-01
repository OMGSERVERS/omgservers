package com.omgservers.service.handler.client.testInterface;

import com.omgservers.service.event.EventModel;
import com.omgservers.service.handler.impl.client.ClientMatchmakerRefCreatedEventHandlerImpl;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ClientMatchmakerRefCreatedEventHandlerImplTestInterface {
    private static final long TIMEOUT = 1L;

    final ClientMatchmakerRefCreatedEventHandlerImpl clientMatchmakerRefCreatedEventHandler;

    public void handle(final EventModel event) {
        clientMatchmakerRefCreatedEventHandler.handle(event)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
