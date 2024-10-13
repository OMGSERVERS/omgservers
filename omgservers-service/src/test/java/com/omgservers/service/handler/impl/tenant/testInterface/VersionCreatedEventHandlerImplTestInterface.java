package com.omgservers.service.handler.impl.tenant.testInterface;

import com.omgservers.service.event.EventModel;
import com.omgservers.service.handler.impl.tenant.TenantVersionCreatedEventHandlerImpl;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class VersionCreatedEventHandlerImplTestInterface {
    private static final long TIMEOUT = 1L;

    final TenantVersionCreatedEventHandlerImpl versionCreatedEventHandler;

    public void handle(final EventModel event) {
        versionCreatedEventHandler.handle(event)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
