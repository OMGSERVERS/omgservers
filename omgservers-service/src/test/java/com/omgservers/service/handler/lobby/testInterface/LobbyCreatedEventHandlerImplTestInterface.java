package com.omgservers.service.handler.lobby.testInterface;

import com.omgservers.schema.event.EventModel;
import com.omgservers.service.handler.lobby.LobbyCreatedEventHandlerImpl;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class LobbyCreatedEventHandlerImplTestInterface {
    private static final long TIMEOUT = 1L;

    final LobbyCreatedEventHandlerImpl lobbyCreatedEventHandler;

    public void handle(final EventModel event) {
        lobbyCreatedEventHandler.handle(event)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
