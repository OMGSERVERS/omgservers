package com.omgservers.service.handler.impl.client.testInterface;

import com.omgservers.service.event.EventModel;
import com.omgservers.service.handler.impl.client.ClientMatchmakerRefDeletedEventHandlerImpl;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ClientMatchmakerRefDeletedEventHandlerImplTestInterface {
    private static final long TIMEOUT = 1L;

    final ClientMatchmakerRefDeletedEventHandlerImpl clientMatchmakerRefDeletedEventHandler;

    public void handle(final EventModel event) {
        clientMatchmakerRefDeletedEventHandler.handle(event)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
