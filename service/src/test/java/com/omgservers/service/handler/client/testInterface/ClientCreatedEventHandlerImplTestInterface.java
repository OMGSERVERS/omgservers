package com.omgservers.service.handler.client.testInterface;

import com.omgservers.model.event.EventModel;
import com.omgservers.service.handler.client.ClientCreatedEventHandlerImpl;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ClientCreatedEventHandlerImplTestInterface {
    private static final long TIMEOUT = 1L;

    final ClientCreatedEventHandlerImpl clientCreatedEventHandler;

    public void handle(final EventModel event) {
        clientCreatedEventHandler.handle(event)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
