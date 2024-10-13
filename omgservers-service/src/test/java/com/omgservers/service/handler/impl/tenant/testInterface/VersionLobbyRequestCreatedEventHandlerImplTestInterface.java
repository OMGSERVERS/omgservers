package com.omgservers.service.handler.impl.tenant.testInterface;

import com.omgservers.service.event.EventModel;
import com.omgservers.service.handler.impl.tenant.TenantLobbyRequestCreatedEventHandlerImpl;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class VersionLobbyRequestCreatedEventHandlerImplTestInterface {
    private static final long TIMEOUT = 1L;

    final TenantLobbyRequestCreatedEventHandlerImpl versionLobbyRequestCreatedEventHandler;

    public void handle(final EventModel event) {
        versionLobbyRequestCreatedEventHandler.handle(event)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}