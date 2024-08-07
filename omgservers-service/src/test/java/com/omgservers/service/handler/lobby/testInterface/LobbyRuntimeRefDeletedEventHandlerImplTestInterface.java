package com.omgservers.service.handler.lobby.testInterface;

import com.omgservers.schema.event.EventModel;
import com.omgservers.service.handler.lobby.LobbyRuntimeRefDeletedEventHandlerImpl;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class LobbyRuntimeRefDeletedEventHandlerImplTestInterface {
    private static final long TIMEOUT = 1L;

    final LobbyRuntimeRefDeletedEventHandlerImpl lobbyRuntimeRefDeletedEventHandler;

    public void handle(final EventModel event) {
        lobbyRuntimeRefDeletedEventHandler.handle(event)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
