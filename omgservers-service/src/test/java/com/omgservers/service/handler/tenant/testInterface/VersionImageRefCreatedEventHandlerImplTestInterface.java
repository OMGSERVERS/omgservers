package com.omgservers.service.handler.tenant.testInterface;

import com.omgservers.service.event.EventModel;
import com.omgservers.service.handler.tenant.VersionImageRefCreatedEventHandlerImpl;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class VersionImageRefCreatedEventHandlerImplTestInterface {
    private static final long TIMEOUT = 1L;

    final VersionImageRefCreatedEventHandlerImpl versionImageRefCreatedEventHandler;

    public void handle(final EventModel event) {
        versionImageRefCreatedEventHandler.handle(event)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
