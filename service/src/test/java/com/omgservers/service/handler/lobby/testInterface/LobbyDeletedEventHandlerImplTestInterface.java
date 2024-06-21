package com.omgservers.service.handler.lobby.testInterface;

import com.omgservers.model.event.EventModel;
import com.omgservers.service.handler.lobby.LobbyDeletedEventHandlerImpl;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class LobbyDeletedEventHandlerImplTestInterface {
    private static final long TIMEOUT = 1L;

    final LobbyDeletedEventHandlerImpl lobbyDeletedEventHandler;

    public void handle(final EventModel event) {
        lobbyDeletedEventHandler.handle(event)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
