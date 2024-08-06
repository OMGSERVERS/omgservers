package com.omgservers.service.handler.client.testInterface;

import com.omgservers.schema.event.EventModel;
import com.omgservers.service.handler.client.ClientRuntimeRefCreatedEventHandlerImpl;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ClientRuntimeRefCreatedEventHandlerImplTestInterface {
    private static final long TIMEOUT = 1L;

    final ClientRuntimeRefCreatedEventHandlerImpl clientRuntimeRefCreatedEventHandler;

    public void handle(final EventModel event) {
        clientRuntimeRefCreatedEventHandler.handle(event)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
