package com.omgservers.service.handler.tenant.testInterface;

import com.omgservers.model.event.EventModel;
import com.omgservers.service.handler.tenant.VersionCreatedEventHandlerImpl;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class VersionCreatedEventHandlerImplTestInterface {
    private static final long TIMEOUT = 1L;

    final VersionCreatedEventHandlerImpl versionCreatedEventHandler;

    public void handle(final EventModel event) {
        versionCreatedEventHandler.handle(event)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
