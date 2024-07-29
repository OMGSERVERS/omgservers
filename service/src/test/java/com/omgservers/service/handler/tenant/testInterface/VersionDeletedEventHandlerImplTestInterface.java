package com.omgservers.service.handler.tenant.testInterface;

import com.omgservers.schema.event.EventModel;
import com.omgservers.service.handler.tenant.VersionDeletedEventHandlerImpl;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class VersionDeletedEventHandlerImplTestInterface {
    private static final long TIMEOUT = 1L;

    final VersionDeletedEventHandlerImpl versionDeletedEventHandler;

    public void handle(final EventModel event) {
        versionDeletedEventHandler.handle(event)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
