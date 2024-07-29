package com.omgservers.service.handler.tenant.testInterface;

import com.omgservers.schema.event.EventModel;
import com.omgservers.service.handler.tenant.VersionLobbyRefCreatedEventHandlerImpl;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class VersionLobbyRefCreatedEventHandlerImplTestInterface {
    private static final long TIMEOUT = 1L;

    final VersionLobbyRefCreatedEventHandlerImpl versionLobbyRefCreatedEventHandler;

    public void handle(final EventModel event) {
        versionLobbyRefCreatedEventHandler.handle(event)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
